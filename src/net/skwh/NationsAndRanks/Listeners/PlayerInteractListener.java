package net.skwh.NationsAndRanks.Listeners;

import net.skwh.NationsAndRanks.Core;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener extends Core implements Listener {
	public void onPlayerInteract(PlayerInteractEvent e) {
		getBaseCore().log("PlayerInteractListener fired!");
	}
}
