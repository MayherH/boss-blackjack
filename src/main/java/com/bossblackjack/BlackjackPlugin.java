package com.bossblackjack;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.Player;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.loottracker.LootReceived;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.List;


@Slf4j
@PluginDescriptor(
		name = "Boss Blackjack"
)
public class BlackjackPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private BlackjackConfig config;

	@Inject
	private ItemManager itemManager;

	@Inject
	private ClientToolbar clientToolbar;

	private BlackjackPanel panel;
	private NavigationButton navButton;

	private final LootSimulator simulator = new LootSimulator();

	private boolean playerStood = false;

	public void onStandPressed(){
		playerStood = true;
	}

	@Override
	protected void startUp() throws Exception
	{
		panel = new BlackjackPanel(this, itemManager);
		BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/icon.png");
		navButton = NavigationButton.builder()
				.tooltip("Boss Blackjack")
				.icon(icon)
				.priority(5)
				.panel(panel)
				.build();
		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navButton);
	}

	@Subscribe
	public void onNpcLootReceived(NpcLootReceived event)
	{
		String npcName = event.getNpc().getName();
		SupportedBoss selectedBoss = config.selectedBoss();

		// check if the npc that dropped loot matches the boss the user selected
		if (npcName == null || !npcName.equalsIgnoreCase(selectedBoss.getNpcName()))
		{
			return;
		}

		int targetValue = config.targetValue();
		int thresholdPercent = config.simThresholdPercent();

		// calculate player's real loot value
		Collection<ItemStack> items = event.getItems();
		long playerTotal = 0;
		for (ItemStack item : items)
		{
			playerTotal += (long) itemManager.getItemPrice(item.getId()) * item.getQuantity();
		}

		// run the simulation
		List<SimulatedDrop> simDrops = simulator.simulate(selectedBoss, targetValue, thresholdPercent);
		long simTotal = 0;
		for (SimulatedDrop drop : simDrops)
		{
			simTotal += (long) itemManager.getItemPrice(drop.getItemId()) * drop.getQuantity();
		}

		// determine winner
		boolean playerBust = playerTotal > targetValue;
		boolean simBust = simTotal > targetValue;
		String result;

		if (playerBust && simBust)
		{
			result = "Both bust! Sim wins.";
		}
		else if (playerBust)
		{
			result = "You bust! Sim wins.";
		}
		else if (simBust)
		{
			result = "Sim busts! You win!";
		}
		else
		{
			long playerDiff = targetValue - playerTotal;
			long simDiff = targetValue - simTotal;
			result = playerDiff <= simDiff ? "You win!" : "Sim wins!";
		}

		log.debug("Player: {} gp | Sim: {} gp | Target: {} gp | {}", playerTotal, simTotal, targetValue, result);
	}

	@Provides
	BlackjackConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BlackjackConfig.class);
	}
}