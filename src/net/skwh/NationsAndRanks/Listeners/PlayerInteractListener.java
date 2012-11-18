package net.skwh.NationsAndRanks.Listeners;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener extends Core implements Listener {
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.hasBlock()) {
			Block b = e.getClickedBlock();
			if (b.getType() == Material.SIGN) {
				Sign s = (Sign) b;
				String signLineTwo = s.getLine(2);
				if (getBaseCore().getNationNames().contains(signLineTwo)) {
					try {
						Nation n = getBaseCore().findNationByName(signLineTwo);
						n.addCitizen(e.getPlayer());
					} catch (Exception ex) {
						getBaseCore().log("There was a problem adding player " + e.getPlayer().getDisplayName() + " to a nation: " + ex.getMessage());
					}
				}
			}
		}
	}
}
