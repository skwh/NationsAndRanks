package net.skwh.NationsAndRanks.Utilites;

import org.bukkit.entity.Player;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Guild;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;

public class JamesBond extends Core {
	public boolean doesPlayerBelongToCountry(Player p) {
		int NationListSize = getBaseCore().getNationList().size();
		for (int i=0;i<NationListSize;i++) {
			Nation n = (Nation) getBaseCore().getNationList().toArray()[i];
			for (int j=0;j<n.getCitizens().size();j++) {
				if (p == n.getCitizens().toArray()[j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean doesPlayerBelongToGuild(Player p) {
		int NationListSize = getBaseCore().getNationList().size();
		for (int i=0;i<NationListSize;i++) {
			Nation n = (Nation) getBaseCore().getNationList().toArray()[i];
			for (int j=0;j<n.getGuilds().size();j++) {
				Guild g = (Guild) n.getGuilds().toArray()[j];
				for (int k=0;k<g.getMembers().size();k++) {
					if (p == g.getMembers().toArray()[k]) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
