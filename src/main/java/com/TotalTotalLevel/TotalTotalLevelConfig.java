package com.TotalTotalLevel;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("TotalTotalLevel")
public interface TotalTotalLevelConfig extends Config
{
	@ConfigItem(
		keyName = "virtualTotalTotalLevel",
		name = "Use virtual levels",
		description = "Use 126 (200M xp) as the max level for skills",
		position = 10
	)
	default boolean virtualTotalTotalLevel()
	{
		return false;
	}
}
