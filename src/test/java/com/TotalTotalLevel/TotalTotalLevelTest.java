package com.TotalTotalLevel;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class TotalTotalLevelTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(TotalTotalLevel.class);
		RuneLite.main(args);
	}
}