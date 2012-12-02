package net.skwh.NationsAndRanks.Listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.defaults.SpawnpointCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Guild;
import net.skwh.NationsAndRanks.Utilites.JamesBond;

public class PlayerRespawnListener extends Core implements Listener {
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player pl = e.getPlayer();
		String s = pl.getName();
		getBaseCore().log("Player " + pl.getName() + " respawned!");
		getBaseCore().log("Does the player belong to a guild? " + JamesBond.doesPlayerBelongToGuild(s));
		if (JamesBond.doesPlayerBelongToGuild(s)) {
			Set<ItemStack> kit = new HashSet<ItemStack>();
			boolean failed = false;
			try {
				kit = JamesBond.getGuildForPlayer(s).getRankForPlayer(s).getKit();
			} catch (Exception e1) {
				failed = true;
				e1.printStackTrace();
			}
			if (!failed) {
				for (ItemStack i1 : kit) {
					if (!pl.getInventory().contains(i1)) {
						pl.getInventory().addItem(i1);
					}
				}
			}
			Location pbRespawn = pl.getBedSpawnLocation();
			if (pbRespawn == null) {
				World playerWorld = pl.getWorld();
				pbRespawn = playerWorld.getSpawnLocation();
			}
			getBaseCore().log("What is the player's respawn point? x:" + pbRespawn.getX() + " y:" + pbRespawn.getY() + " z:" + pbRespawn.getZ());
			Guild g = JamesBond.getGuildForPlayer(s);
			Location gSP = g.getSpawnPoint();
			if (pbRespawn != gSP) {
				Command spawnCommand = new SpawnpointCommand();
				String[] args = {pl.getName(),
						String.valueOf(gSP.getX()),
						String.valueOf(gSP.getY()),
						String.valueOf(gSP.getZ())
				};
				spawnCommand.execute(pl, "Set spawn", args);
			}
		}
	}
}
