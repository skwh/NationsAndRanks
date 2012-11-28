package net.skwh.NationsAndRanks.Utilites;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Guild;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;
import net.skwh.NationsAndRanks.BaseTemplates.Rank;
import net.skwh.NationsAndRanks.BaseTemplates.User;

public class JamesBond extends Core {
	public boolean doesPlayerBelongToCountry(Player p) {
		if (!isPlayerInUserList(p)) {
			for (Nation n : getBaseCore().getNationList()) {
				for (Player pl : n.getCitizens()) {
					if (p == pl) {
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
			for (Nation n : getBaseCore().getNationList()) {
				for (Guild g : n.getGuilds()) {
					for (Player pl : g.getMembers()) {
						if (p == pl) {
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
			for (Nation n : getBaseCore().getNationList()) {
				for (Guild g : n.getGuilds()) {
					GuildNames.add(g.getName());
				}
			}
			return GuildNames;
	}
	
	public HashMap<String,Guild> getGuilds() {
		HashMap<String,Guild> GuildSet = new HashMap<String,Guild>();
		for (Nation n : getBaseCore().getNationList()) {
			for (Guild g : n.getGuilds()) {
				GuildSet.put(g.getName(),g);
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
			for (String st : GuildNames) {
				Guild currentGuild = hashGuilds.get(st);
				for (Player pl : currentGuild.getMembers()) {
					if (p == pl) {
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
			for (String st : NationNames) {
				Nation currentNation = hashNations.get(st);
				for (Player pl : currentNation.getCitizens()) {
					if (p == pl) {
						return currentNation;
					}
				}
			}
		}
		return null;
	}
	
	public Set<String> rankGetNames(Set<Rank> arg0) {
		Set<String> names = new HashSet<String>();
		for (Rank r : arg0) {
			names.add(r.getName());
		}
		return names;
	}
	
	public Set<String> guildGetNames(Set<Guild> arg0) {
		Set<String> names = new HashSet<String>();
		for (Guild g : arg0) {
			names.add(g.getName());
		}
		return names;
	}
	
	public Set<String> nationGetNames(Set<Nation> arg0) {
		Set<String> names = new HashSet<String>();
		for (Nation n : arg0) {
			names.add(n.getName());
		}
		return names;
	}
	
	public boolean isPlayerInUserList(Player p) {
		return getBaseCore().getUserList().containsKey(p);
	}
	
	public boolean isUserinUserList(User u) {
		return getBaseCore().getUserList().containsValue(u);
	}
}
