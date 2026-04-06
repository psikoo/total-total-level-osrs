package com.TotalTotalLevel;

import com.google.inject.Provides;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.ScriptCallbackEvent;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Total Total Level"
)
public class TotalTotalLevel extends Plugin
{
	@Inject
	private Client client;
	
	@Inject
	private ClientThread clientThread;
	
	@Inject
	private TotalTotalLevelConfig config;
	
	@Provides
	TotalTotalLevelConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TotalTotalLevelConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		clientThread.invoke(this::simulateSkillChange);
	}

	@Override
	protected void shutDown() throws Exception
	{
		clientThread.invoke(this::simulateSkillChange);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged configChanged)
	{
		if (!configChanged.getGroup().equals("TotalTotalLevel")) return;
		clientThread.invoke(this::simulateSkillChange);
	}

	@Subscribe(priority = -1.0f)
	public void onScriptCallbackEvent(ScriptCallbackEvent e)
	{
		final String eventName = e.getEventName();
		final Object[] objStack = client.getObjectStack();
		final int objStackSize = client.getObjectStackSize();

		if (eventName.equals("skillTabTotalLevel"))
		{
			// Get total level from the displayed total level to be compatible with things like virtual levels
			int displayedTotalLevel = Integer.parseInt(((String) objStack[objStackSize - 1]).split(":")[1].trim());
			objStack[objStackSize  - 1] = "Total level: " + displayedTotalLevel + " / " + getTotalTotalLevel();
		}
	}

	private void simulateSkillChange()
	{
		// Trigger skill tab update
		for (Skill skill : Skill.values()) { client.queueChangedSkill(skill); }
	}

	private int getTotalTotalLevel()
	{
		int skillCount = Skill.values().length;
		return (config.virtualTotalTotalLevel() == true ? skillCount * 126 : skillCount * 99);
	}
}
