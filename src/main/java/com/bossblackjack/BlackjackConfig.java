package com.bossblackjack;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("bossblackjack")
public interface BlackjackConfig extends Config
{
	@ConfigItem(
		keyName = "targetValue",
		name = "Target Value (GP)",
		description = "The GP value to try to get closest to without going over"
	)
	default int targetValue()
	{
		return 100000;
	}

	@ConfigItem(
			keyName = "simThresholdPercent",
			name = "Sim Hit Threshold (%)",
			description = "The sim will keep rolling if its total is below this % of the target value"
	)
	default int simThresholdPercent()
	{
		return 50;
	}

	@ConfigItem(
			keyName = "selectedBoss",
			name = "Boss",
			description = "The boss you are farming"
	)
	default SupportedBoss selectedBoss()
	{
		return SupportedBoss.VARDORVIS;
	}
}
