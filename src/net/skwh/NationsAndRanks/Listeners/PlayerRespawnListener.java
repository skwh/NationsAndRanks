package net.skwh.NationsAndRanks.Listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
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
import net.skwh.NationsAndRanks.Utilites.JamesBond;

public class PlayerRespawnListener extends Core implements Listener {
	private static final JamesBond DoubleOhSeven = new JamesBond();
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player pl = e.getPlayer();
		String s = pl.getName();
		getBaseCore().log(ChatColor.DARK_AQUA + "Player " + pl.getName() + " respawned!");
		getBaseCore().log(ChatColor.DARK_AQUA + "Does the player belong to a guild? " + DoubleOhSeven.doesPlayerBelongToGuild(s));
		if (DoubleOhSeven.doesPlayerBelongToGuild(s)) {
			Set<ItemStack> kit = new HashSet<ItemStack>();
			boolean failed = false;
			try {
				kit = DoubleOhSeven.getGuildForPlayer(s).getRankForPlayer(s).getKit();
			} catch (Exception e1) {
				failed = true;
				getBaseCore().log(e1.getMessage());
			}
			if (!failed) {
				for (ItemStack i1 : kit) {
					pl.getInventory().addItem(i1);
				}
			}
			
			Location pbRespawn = pl.getBedSpawnLocation();
			if (pbRespawn == null) {
				World playerWorld = pl.getWorld();
				pbRespawn = playerWorld.getSpawnLocation();
			}
			getBaseCore().log("What is the player's respawn point? x:" + pbRespawn.getX() + " y:" + pbRespawn.getY() + " z:" + pbRespawn.getZ());
			Location gSP = DoubleOhSeven.getGuildForPlayer(s).getSpawnPoint();
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
