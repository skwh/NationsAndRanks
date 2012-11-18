package net.skwh.NationsAndRanks.Listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import net.skwh.NationsAndRanks.Core;

public class PlayerChatListener extends Core implements Listener {
	final private String format = " %1$s > %2$s";
	
	@SuppressWarnings("deprecation")
	public void onPlayerChat(PlayerChatEvent e) {
		e.setFormat(this.format);
	}

}
