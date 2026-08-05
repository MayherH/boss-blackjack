package com.bossblackjack;

import com.google.gson.FieldAttributes;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ServerNpcLoot;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;
import net.runelite.http.api.loottracker.LootRecordType;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.List;
import java.util.Map;


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

	private long playerTotal = 0;
	private long simTotal = 0;

	private final LootSimulator simulator = new LootSimulator();

	@Override
	protected void startUp() throws Exception
	{
		panel = new BlackjackPanel(this, itemManager);
		BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/BBJicon.png");
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
	public void onServerNpcLoot(final ServerNpcLoot event)
	{
		final NPCComposition npc = event.getComposition();
		final Collection<ItemStack> items = event.getItems();
		final String name = Text.removeTags(npc.getName());

		if (name.equalsIgnoreCase(config.selectedBoss().getNpcName())){
			for (ItemStack item : items){
				int itemId = item.getId();
				int quantity = item.getQuantity();
				panel.getPlayerGrid().addLootItem(itemId, quantity);
			}
		}
		int gridValue = calculateGridValue(panel.getPlayerGrid());
		panel.getPlayerGrid().setRightText(gridValue + " gp");

	}

	public int calculateGridValue(GridPanel grid)
	{
		int totalValue = 0;

		if (grid == null)
		{
			return 0;
		}

		for (Map.Entry<Integer, Integer> entry : grid.getItemQuantities().entrySet())
		{
			int itemId = entry.getKey();
			int quantity = entry.getValue();

			totalValue += itemManager.getItemPrice(itemId) * quantity;
		}

		return totalValue;
	}


	@Getter
	public long getPlayerTotal;

	@Getter
	public long getSimTotal;





	@Provides
	BlackjackConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BlackjackConfig.class);
	}
}