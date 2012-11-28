package net.skwh.NationsAndRanks.Listeners;

import java.util.HashSet;
import java.util.Set;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;
import net.skwh.NationsAndRanks.Utilites.JamesBond;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener extends Core implements Listener {
	private static final JamesBond DoubleOhSeven = new JamesBond();
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		getBaseCore().log("Player joined!");
		Player p = e.getPlayer();
		if (DoubleOhSeven.doesPlayerBelongToCountry(p)) {
			DoubleOhSeven.getNationForPlayer(p).refreshCitizens();
		}
		
		if (DoubleOhSeven.doesPlayerBelongToGuild(p)) {
			boolean failed = false;
			Set<ItemStack> i = new HashSet<ItemStack>();
			try {
				i = DoubleOhSeven.getGuildForPlayer(p).getRankForPlayer(p).getKit();
			} catch (Exception ex) {
				failed = true;
				getBaseCore().log("There was a problem giving the player " + p.getDisplayName() + " their rank's kit: " + ex.getMessage());
			} finally {
				if (!failed) {
					for (ItemStack jk : i) {
						p.getInventory().addItem(jk);
					}
				}
			}
		}
	}
}
