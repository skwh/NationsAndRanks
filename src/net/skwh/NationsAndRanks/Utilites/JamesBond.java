package net.skwh.NationsAndRanks.Utilites;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
	 * Sets a player to the given rank in both the user object and the guild object, creating fewer errors.
	 * @param s {@link String} Username
	 * @param r {@link Rank}
	 * @return {@link Boolean} if the operation completed successfully.
	 */
	public static boolean globalSetPlayerToRank(String s, Rank r) {
		if (doesPlayerBelongToGuild(s)) {
			if (isPlayerInUserList(s)) {
				User u = getBaseCore().getUserList().get(s);
				u.setCurrentRankGuild(r);
				u.getNation().refreshCitizens();
				return true;
			}
		}
		return false;
	}
	/**
	 * Sets a player to the given guild and sets their rank to the lowest rank avaliable, creating fewer errors.
	 * @param s {@link String} Username
	 * @param g {@link Guild}
	 * @return {@link Boolean} if the operation completed successfully.
	 */
	public static boolean globalSetPlayerInGuild(String s, Guild g) {
		if (doesPlayerBelongToCountry(s)) {
			if (isPlayerInUserList(s)) {
				User u = getBaseCore().getUserList().get(s);
				u.setGuild(true, g);
				globalSetPlayerToRank(s, g.getRanksInOrder().firstElement());
				return true;
			}
		}
		return false;
	}
	/**
	 * Sets a player as a member of the given nation.
	 * @param s {@link String} Username
	 * @param n {@link Nation}
	 * @return {@link Boolean} if the operation completed successfully.
	 */
	public static boolean globalSetPlayerInNation(String s, Nation n) {
		if (isPlayerInUserList(s)) {
			User u = getBaseCore().getUserList().get(s);
			u.setNation(true, n);
			return true;
		}
		return false;
	}
	/**
	 * Returns whether or not the given player belongs to a nation.
	 * @param s {@link String} Username
	 * @return {@link Boolean}
	 */
	public static boolean doesPlayerBelongToCountry(String s) {
		if (!isPlayerInUserList(s)) {
			for (Nation n : getBaseCore().getNationList()) {
				for (String pl : n.getCitizens()) {
					if (s == pl) {
						return true;
					}
				}
			}
			return false;
		} else {
			User u = getBaseCore().getUserList().get(s);
			return u.doesBelongToNation();
		}
	}
	
	/**
	 * Returns whether or not the given player belongs to a guild.
	 * @param p {@link Player}
	 * @return {@link Boolean}
	 */
	public static boolean doesPlayerBelongToGuild(String s) {
			if (!isPlayerInUserList(s)) {
			for (Nation n : getBaseCore().getNationList()) {
				for (Guild g : n.getGuilds()) {
					for (String pl : g.getMembers()) {
						if (s == pl) {
							return true;
						}
					}
				}
			}
			return false;
		} else {
			User u = getBaseCore().getUserList().get(s);
			return u.doesBelongToGuild();
		}
	}
	
	/**
	 * Returns a set of all the guild names.
	 * @return {@link Set}
	 */
	public static Set<String> getGuildNames() {
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
	public static HashMap<String,Guild> getGuilds() {
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
	public static Guild getGuildForPlayer(String s) {
		if (isPlayerInUserList(s)) {
			if (getBaseCore().getUserList().get(s).getGuild() != null) {
				return getBaseCore().getUserList().get(s).getGuild();
			}
		} else {
			HashMap<String,Guild> hashGuilds = getGuilds();
			Set<String> GuildNames = getGuildNames();
			for (String st : GuildNames) {
				Guild currentGuild = hashGuilds.get(st);
				for (String pl : currentGuild.getMembers()) {
					if (s == pl) {
						return currentGuild;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns the nation a player belongs to, if any.
	 * @param s {@link String}
	 * @return {@link Nation}
	 */
	public static Nation getNationForPlayer(String s) {
		if (isPlayerInUserList(s)) {
			if (getBaseCore().getUserList().get(s).getNation() != null) {
				return getBaseCore().getUserList().get(s).getNation();
			}
		} else {
			HashMap<String,Nation> hashNations = getBaseCore().getNation_NameList();
			Set<String> NationNames = getBaseCore().getNationNames();
			for (String st : NationNames) {
				Nation currentNation = hashNations.get(st);
				for (String pl : currentNation.getCitizens()) {
					if (s == pl) {
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
	public static Set<String> rankGetNames(Set<Rank> arg0) {
		Set<String> names = new HashSet<String>();
		for (Rank r : arg0) {
			names.add(r.getName());
		}
		return names;
	}
	public static Vector<String> rankGetNames(Vector<Rank> arg0) {
		Vector<String> names = new Vector<String>();
		for (Rank r : arg0) {
			names.addElement(r.getName());
		}
		return names;
	}
	
	/**
	 * Displays the {@link Guild#ranksInOrder} as a structured list.
	 * @param arg0 {@link Vector}[{@link Rank}] ranks in order
	 * @return {@link String} Hierarchy
	 */
	public static String displayRankHierarchy(Vector<Rank> arg0) {
		String harcy = "";
		for (Rank r : arg0) {
			harcy += "~" + r.getName() + "\n";
			try {
				harcy += " +" + r.getUp().getName() + "\n";
			} catch (NullPointerException e) {
				harcy += " +NULL\n";
			}
			try {
				harcy += " -" + r.getDown().getName() + "\n";
			} catch (NullPointerException e) {
				harcy += " -NULL\n";
			}
			harcy += "----\n";
		}
		return harcy;
	}
	/**
	 * Extracts the names out of the given set of guilds and returns it as a set of strings.
	 * @param arg0 {@link Set}&#60;{@link Guild}&#62;
	 * @return names {@link Set}&#60;{@link String}&#62;
	 */
	public static Set<String> guildGetNames(Set<Guild> arg0) {
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
	public static Set<String> nationGetNames(Set<Nation> arg0) {
		Set<String> names = new HashSet<String>();
		for (Nation n : arg0) {
			names.add(n.getName());
		}
		return names;
	}
	
	/**
	 * Returns whether or not the given player is in the User list ({@link Core#getUserList()}).
	 * @param s {@link String} User name
	 * @return {@link Boolean}
	 */
	public static boolean isPlayerInUserList(String s) {
		return getBaseCore().getUserList().containsKey(s);
	}
	
	/**
	 * Returns the username of the player associated with the passed User.
	 * @param u {@link User}
	 * @return {link String} userName
	 */
	public static String getPlayerForUser(User u) {
		return u.getUserName();
	}
	
	/**
	 * Returns the User associated with the given player's username.
	 * @param s {@link String} userName
	 * @return {@link User}
	 */
	public static User getUserForPlayer(String s) {
		if (isPlayerInUserList(s)) {
			return getBaseCore().getUserList().get(s);
		} else {
			return null;
		}
	}
	/**
	 * Returns whether or not the given user is in the User list ({@link Core#getUserList()}).
	 * @param u {@link User}
	 * @return {@link Boolean}
	 */
	public static boolean isUserinUserList(User u) {
		return getBaseCore().getUserList().containsValue(u);
	}
	/**
	 * Returns true if the given rank name exists in any guild.
	 * @param s {@link String} Rank Name
	 * @return {@link Boolean}
	 */
	public static boolean doesRankExist(String s) {
		HashMap<String,Guild> guildList = getGuilds();
		Set<String> guildNames = getGuildNames();
		for (String st : guildNames) {
			Guild g = guildList.get(st);
			for (Rank r : g.getRanks()) {
				if (r.getName() == s) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Returns true if the given rank name exists in the given guild.
	 * @param s {@link String} Rank Name
	 * @param g {@link Guild}
	 * @return {@link Boolean}
	 */
	public static boolean doesRankExistInGuild(String s, Guild g) {
		Set<Rank> rankList = g.getRanks();
		for (Rank r : rankList) {
			if (s.equalsIgnoreCase(r.getName())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Finds the rank in the given guild, if it doesn't exist, returns null.
	 * @param s {@link String} Rank Name
	 * @param g {@link Guild}
	 * @return {@link Rank}
	 */
	public static Rank getRankInGuild(String s, Guild g) {
		Set<Rank> rankList = g.getRanks();
		for (Rank r: rankList) {
			if (r.getName() == s) {
				return r;
			}
		}
		return null;
	}
	/**
	 * Loops through {@link PlayerInventory#getArmorContents()} to see if the given {@link ItemStack} is included.
	 * <br> Equivalent to {@link inventory#contains} for {@link PlayerInventory}.
	 * @param pInv {@link PlayerInventory}
	 * @param i {@link ItemStack}
	 * @return {@link Boolean}
	 */
	public static boolean itemIsInArmorInventory(PlayerInventory pInv, ItemStack i) {
		for (ItemStack is : pInv.getArmorContents()) {
			if (is == i) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns information about the given Player.
	 * @param p {@link Player}
	 * @return String[]
	 */
	public static String[] getInformation(Player p) {
		boolean isPlayerCM = doesPlayerBelongToCountry(p.getName());
		boolean isPlayerGM = doesPlayerBelongToGuild(p.getName());
		boolean doesUserExist = getBaseCore().getUserList().containsKey(p.getName());
		String countryName, guildName, rankName, userName;
		if (isPlayerCM) {
			countryName = getNationForPlayer(p.getName()).getName();
			if (isPlayerGM) {
				guildName = getGuildForPlayer(p.getName()).getName();
				rankName = getGuildForPlayer(p.getName()).getRankForPlayer(p.getName()).getName();
			}
		}
		
		
		String[] info = {Boolean.toString(isPlayerCM),Boolean.toString(isPlayerGM),Boolean.toString(doesUserExist)};
		return info;
	}
	
	/**
	 * Returns information about the given Player by interpreting their username.
	 * @param s {@link String} Username
	 * @return String[]
	 */
	public static String[] getInformation(String s) {
		String[] info = {};
		return info;
	}
	/**
	 * Reads strings and attempts to extract the corresponding {@link ChatColor} out of them. 
	 * If it can't figure out what the string is, it returns {@link ChatColor#WHITE}.
	 * @param s {@link String}
	 * @return {@link ChatColor}
	 */
	public static ChatColor interpretColor(String s) {
		if (s == null) {
			return ChatColor.WHITE;
		}
		if (s.equalsIgnoreCase("aqua")) {
			return ChatColor.AQUA;
		}
		if (s.equalsIgnoreCase("black")) {
			return ChatColor.BLACK;
		}
		if (s.equalsIgnoreCase("blue")) {
			return ChatColor.BLUE;
		}
		if (s.equalsIgnoreCase("bold")) {
			return ChatColor.BOLD;
		}
		if (s.equalsIgnoreCase("dark aqua")) {
			return ChatColor.DARK_AQUA;
		}
		if (s.equalsIgnoreCase("dark blue")) {
			return ChatColor.DARK_BLUE;
		}
		if (s.equalsIgnoreCase("dark gray")) {
			return ChatColor.DARK_GRAY;
		}
		if (s.equalsIgnoreCase("dark green")) {
			return ChatColor.DARK_GREEN;
		}
		if (s.equalsIgnoreCase("dark purple")) {
			return ChatColor.DARK_PURPLE;
		}
		if (s.equalsIgnoreCase("dark red")) {
			return ChatColor.DARK_RED;
		}
		if (s.equalsIgnoreCase("gold")) {
			return ChatColor.GOLD;
		}
		if (s.equalsIgnoreCase("gray") || s.equalsIgnoreCase("grey")) {
			return ChatColor.GRAY;
		}
		if (s.equalsIgnoreCase("green")) {
			return ChatColor.GREEN;
		}
		if (s.equalsIgnoreCase("italic")) {
			return ChatColor.ITALIC;
		}
		if (s.equalsIgnoreCase("light purple")) {
			return ChatColor.LIGHT_PURPLE;
		}
		if (s.equalsIgnoreCase("magic")) {
			return ChatColor.MAGIC;
		}
		if (s.equalsIgnoreCase("red")) {
			return ChatColor.RED;
		}
		if (s.equalsIgnoreCase("RESET")) {
			return ChatColor.RESET;
		}
		if (s.equalsIgnoreCase("strikethrough")) {
			return ChatColor.STRIKETHROUGH;
		}
		if (s.equalsIgnoreCase("underline")) {
			return ChatColor.UNDERLINE;
		}
		if (s.equalsIgnoreCase("White")) {
			return ChatColor.WHITE;
		}
		if (s.equalsIgnoreCase("yellow")) {
			return ChatColor.YELLOW;
		}
		getBaseCore().log("Couldn't understand color " + s);
		return ChatColor.WHITE;
	}
}
