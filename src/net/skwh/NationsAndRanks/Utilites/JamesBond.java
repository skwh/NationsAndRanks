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
/**
 * A class for finding information.
 * @author Evan Derby <somekidwithhtml@gmail.com>
 * @version 0.8
 * @since 2012-11-29
 */
public class JamesBond extends Core {
	
	/**
	 * Returns whether or not the given player belongs to a nation.
	 * @param p {@link Player}
	 * @return {@link Boolean}
	 */
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
	
	/**
	 * Returns whether or not the given player belongs to a guild.
	 * @param p {@link Player}
	 * @return {@link Boolean}
	 */
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
	
	/**
	 * Returns a set of all the guild names.
	 * @return {@link Set}
	 */
	public Set<String> getGuildNames() {
			Set<String> GuildNames = new HashSet<String>();
			for (Nation n : getBaseCore().getNationList()) {
				for (Guild g : n.getGuilds()) {
					GuildNames.add(g.getName());
				}
			}
			return GuildNames;
	}
	
	/**
	 * Returns a hashmap of guild names mapped to their objects.
	 * @return {@link HashMap}
	 */
	public HashMap<String,Guild> getGuilds() {
		HashMap<String,Guild> GuildSet = new HashMap<String,Guild>();
		for (Nation n : getBaseCore().getNationList()) {
			for (Guild g : n.getGuilds()) {
				GuildSet.put(g.getName(),g);
			}
		}
		return GuildSet;
	}
	
	/**
	 * Returns the guild a player belongs to, if any.
	 * @param p {@link Player}
	 * @return {@link Guild}
	 */
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
	
	/**
	 * Returns the nation a player belongs to, if any.
	 * @param p {@link Player}
	 * @return {@link Nation}
	 */
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
	
	/**
	 * Extracts the names out of the given set of ranks and returns it as a set of strings.
	 * @param arg0 {@link Set}&#60;{@link Rank}&#62;
	 * @return names {@link Set}&#60;{@link String}&#62;
	 */
	public Set<String> rankGetNames(Set<Rank> arg0) {
		Set<String> names = new HashSet<String>();
		for (Rank r : arg0) {
			names.add(r.getName());
		}
		return names;
	}
	
	/**
	 * Extracts the names out of the given set of guilds and returns it as a set of strings.
	 * @param arg0 {@link Set}&#60;{@link Guild}&#62;
	 * @return names {@link Set}&#60;{@link String}&#62;
	 */
	public Set<String> guildGetNames(Set<Guild> arg0) {
		Set<String> names = new HashSet<String>();
		for (Guild g : arg0) {
			names.add(g.getName());
		}
		return names;
	}
	
	/**
	 * Extracts the names out of the given set of nations and returns it as a set of strings.
	 * @param arg0 {@link Set}&#60;{@link Nation}&#62;
	 * @return names {@link Set}&#60;{@link String}&#62;
	 */
	public Set<String> nationGetNames(Set<Nation> arg0) {
		Set<String> names = new HashSet<String>();
		for (Nation n : arg0) {
			names.add(n.getName());
		}
		return names;
	}
	
	/**
	 * Returns whether or not the given player is in the User list ({@link Core#getUserList()}).
	 * @param p {@link Player}
	 * @return {@link Boolean}
	 */
	public boolean isPlayerInUserList(Player p) {
		return getBaseCore().getUserList().containsKey(p);
	}
	
	/**
	 * Returns whether or not the given user is in the User list ({@link Core#getUserList()}).
	 * @param u {@link User}
	 * @return {@link Boolean}
	 */
	public boolean isUserinUserList(User u) {
		return getBaseCore().getUserList().containsValue(u);
	}
}
