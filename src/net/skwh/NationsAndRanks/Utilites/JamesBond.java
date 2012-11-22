package net.skwh.NationsAndRanks.Utilites;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Guild;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;
import net.skwh.NationsAndRanks.BaseTemplates.User;

public class JamesBond extends Core {
	public boolean doesPlayerBelongToCountry(Player p) {
		if (!isPlayerInUserList(p)) {
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
		} else {
			User u = getBaseCore().getUserList().get(p);
			return u.doesBelongToNation();
		}
	}
	
	public boolean doesPlayerBelongToGuild(Player p) {
			if (!isPlayerInUserList(p)) {
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
		} else {
			User u = getBaseCore().getUserList().get(p);
			return u.doesBelongToGuild();
		}
	}
	
	public Set<String> getGuildNames() {
			Set<String> GuildNames = new HashSet<String>();
			int NationListSize = getBaseCore().getNationList().size();
			for (int i=0;i<NationListSize;i++) {
				Nation cNation = (Nation) getBaseCore().getNationList().toArray()[i];
				int GuildListSize = cNation.getGuilds().size();
				for (int j=0;j<GuildListSize;j++) {
					GuildNames.add(((Guild) cNation.getGuilds().toArray()[i]).getName());
				}
			}
			return GuildNames;
	}
	
	public HashMap<String,Guild> getGuilds() {
		HashMap<String,Guild> GuildSet = new HashMap<String,Guild>();
		int NationListSize = getBaseCore().getNationList().size();
		for (int i=0;i<NationListSize;i++) {
			Nation cNation = (Nation) getBaseCore().getNationList().toArray()[i];
			int GuildListSize = cNation.getGuilds().size();
			for (int j=0;j<GuildListSize;j++) {
				Guild CurrentGuild = (Guild) cNation.getGuilds().toArray()[j];
				GuildSet.put(CurrentGuild.getName(),CurrentGuild);
			}
		}
		return GuildSet;
	}
	
	public Guild getGuildForPlayer(Player p) {
		if (isPlayerInUserList(p)) {
			return getBaseCore().getUserList().get(p).getGuild();
		} else {
			HashMap<String,Guild> hashGuilds = getGuilds();
			Set<String> GuildNames = getGuildNames();
			for (int i=0;i<GuildNames.size();i++) {
				Guild currentGuild = hashGuilds.get(GuildNames.toArray()[i]);
				for (int a=0;a<currentGuild.getMembers().size();a++) {
					if ((Player)currentGuild.getMembers().toArray()[a] == p) {
						return currentGuild;
					}
				}
			}
		}
		return null;
	}
	
	public Nation getNationForPlayer(Player p) {
		if (isPlayerInUserList(p)) {
			return getBaseCore().getUserList().get(p).getNation();
		} else {
			HashMap<String,Nation> hashNations = getBaseCore().getNation_NameList();
			Set<String> NationNames = getBaseCore().getNationNames();
			for (int i=0;i<NationNames.size();i++) {
				Nation currentNation = hashNations.get(NationNames.toArray()[i]);
				for (int a=0;a<currentNation.getCitizens().size();a++) {
					if ((Player) currentNation.getCitizens().toArray()[a] == p) {
						return currentNation;
					}
				}
			}
		}
		return null;
	}
	
	public boolean isPlayerInUserList(Player p) {
		return getBaseCore().getUserList().containsKey(p);
	}
	
	public boolean isUserinUserList(User u) {
		return getBaseCore().getUserList().containsValue(u);
	}
}
