package net.skwh.NationsAndRanks.Listeners;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Guild;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;
import net.skwh.NationsAndRanks.Utilites.JamesBond;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener extends Core implements Listener {
	private final static JamesBond DoubleOhSeven = new JamesBond();
	public void onPlayerInteract(PlayerInteractEvent e) {
		getBaseCore().log("PlayerInteractListener fired!");
		if (e.hasBlock()) {
			Block b = e.getClickedBlock();
			if (b.getType() == Material.SIGN) {
				Sign s = (Sign) b;
				String signLineOne = s.getLine(1);
				String signLineTwo = s.getLine(2);
				if (signLineOne == "Join") {
					if (getBaseCore().getNationNames().contains(signLineTwo) && !DoubleOhSeven.doesPlayerBelongToCountry(e.getPlayer())) {
						boolean failed = false;
						try {
							Nation n = getBaseCore().findNationByName(signLineTwo);
							n.addCitizen(e.getPlayer());
						} catch (Exception ex) {
							failed = true;
							getBaseCore().log("There was a problem adding player " + e.getPlayer().getDisplayName() + " to a nation: " + ex.getMessage());
						} finally {
							if (failed) {
								e.getPlayer().sendMessage(ChatColor.RED + "Sorry, there was a problem adding you to this nation.");
							} else {
								e.getPlayer().sendMessage(ChatColor.GREEN + "Welcome to this nation," + e.getPlayer().getDisplayName() + "!");
							}
						}
					} else if (DoubleOhSeven.getGuildNames().contains(signLineTwo) && !DoubleOhSeven.doesPlayerBelongToGuild(e.getPlayer()) && DoubleOhSeven.doesPlayerBelongToCountry(e.getPlayer())) {
						boolean failed = false;
						try {
							Guild g = DoubleOhSeven.getGuilds().get(signLineTwo);
							g.addMember(e.getPlayer());
						} catch (Exception ex) {
							failed = true;
							getBaseCore().log("There was an error adding player " + e.getPlayer().getDisplayName() + " to a guild: " + ex.getMessage());
						} finally {
							if (failed) {
								e.getPlayer().sendMessage(ChatColor.RED + "Sorry, there was a problem adding you to this guild.");
							} else {
								e.getPlayer().sendMessage(ChatColor.GREEN + "Welcome to the guild, " + e.getPlayer().getDisplayName() + "!");
							}
						}
					}
				}
			}
		}
	}
}
