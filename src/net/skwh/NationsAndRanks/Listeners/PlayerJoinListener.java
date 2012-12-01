package net.skwh.NationsAndRanks.Listeners;

import java.util.HashSet;
import java.util.Set;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Guild;
import net.skwh.NationsAndRanks.BaseTemplates.Rank;
import net.skwh.NationsAndRanks.BaseTemplates.User;
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
		Player p = e.getPlayer();
		String s = p.getName();
		getBaseCore().log("Player " + p.getName() + " joined!");
		getBaseCore().log("Has the player played before? " + p.hasPlayedBefore());
		getBaseCore().log("Does the player belong to a country? " + DoubleOhSeven.doesPlayerBelongToCountry(s));
		if (DoubleOhSeven.doesPlayerBelongToCountry(s)) {
			DoubleOhSeven.getNationForPlayer(s).refreshCitizens();
		}
		getBaseCore().log("Is the player in the user list? " + DoubleOhSeven.isPlayerInUserList(s));
		if (!DoubleOhSeven.isPlayerInUserList(s)) {
			User u = new User(p);
			getBaseCore().getUserList().put(s, u);
			getBaseCore().log("A new user was added to the userlist, " + p.getName());
		}
		
		getBaseCore().log("Does the player belong to a guild? " + DoubleOhSeven.doesPlayerBelongToGuild(s));
		if (DoubleOhSeven.doesPlayerBelongToGuild(s)) {
			boolean failed = false;
			Set<ItemStack> i = new HashSet<ItemStack>();
			try {
				Guild g = DoubleOhSeven.getGuildForPlayer(s);
				Rank r = g.getRankForPlayer(s);
				i = r.getKit();
			} catch (Exception ex) {
				failed = true;
				ex.printStackTrace();
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
