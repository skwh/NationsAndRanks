package net.skwh.NationsAndRanks.Listeners;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends Core implements Listener {
	public void onPlayerJoin(PlayerJoinEvent e) {
		getBaseCore().log("Player joined!");
		Player p = e.getPlayer();
		int nnLength = getBaseCore().getNationNames().toArray().length;
		for (int i=0;i<nnLength;i++) {
			Nation n = getBaseCore().getNation_NameList().get((String) getBaseCore().getNationNames().toArray()[i]);
			getBaseCore().log((String) getBaseCore().getNationNames().toArray()[i]);
			if (n.getCitizens().contains(p)) {
				n.refreshCitizens();
			}
		}
	}
}
