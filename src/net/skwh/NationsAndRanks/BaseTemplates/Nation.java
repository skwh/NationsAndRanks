package net.skwh.NationsAndRanks.BaseTemplates;

import java.util.HashSet;
import java.util.Set;

import net.skwh.NationsAndRanks.Core;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
/**
 * The template for a nation.
 * @author Evan Derby <somekidwithhtml@gmail.com>
 * @version 0.8
 * @since 2012-11-29
 */
public class Nation {
	/**
	 * The local reference to the main {@link Core}.
	 */
	private final Core baseCore;
	/**
	 * The name of the Nation.
	 */
	private final String name;
	/**
	 * Short name of the Nation.
	 * @deprecated
	 */
	private String short_name;
	/**
	 * The set of all players' usernames belonging to this Nation.
	 */
	private Set<String> citizens = new HashSet<String>();
	/**
	 * The Set of all guilds contained in this Nation.
	 */
	private Set<Guild> guilds = new HashSet<Guild>();
	/**
	 * The chat color for this Nation.
	 */
	private ChatColor uniqueColor;
	
	/**
	 * Sets the uniqueColor. Used by {@link CommandsExecutor#onCommand()}.
	 * @param s -String: The new color to be assigned.
	 * @throws Exception
	 */
	public void setColor(String s) throws Exception {
		boolean oops = false;
		switch (s) {
			case "blue":
				uniqueColor = ChatColor.BLUE;
				break;
			case "red":
				uniqueColor = ChatColor.RED;
				break;
			case "green":
				uniqueColor = ChatColor.GREEN;
				break;
			case "yellow":
				uniqueColor = ChatColor.YELLOW;
				break;
			case "grey":
				uniqueColor = ChatColor.GRAY;
				break;
			case "gold":
				uniqueColor = ChatColor.GOLD;
				break;
			default:
				oops = true;
				break;
		}
		if (oops) {
			throw new Exception("No Matching Color Found");
		} else {
			this.refreshCitizens();
		}
	}
	/**
	 * The getter for this nation's chat color.
	 * @return ChatColor uniqueColor
	 */
	public ChatColor getColor() {
		return uniqueColor;
	}
	
	/**
	 * Gets the name of this Nation.
	 * @return String name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Gets the {@link Core} for this Nation.
	 * @return Core baseCore
	 */
	public Core getCore() {
		return baseCore;
	}
	
	/**
	 * Sets the short name for this Nation.
	 * @deprecated
	 * @param s String
	 */
	public void setShortName(String s) {
		short_name = s;
	}
	/**
	 * Gets the short name for this Nation.
	 * @deprecated
	 * @return
	 */
	public String getShortName() {
		return short_name;
	}
	/**
	 * Gets the set of {@link #citizens}.
	 * @return
	 */
	public Set<String> getCitizens() {
		return citizens;
	}
	/**
	 * Adds a player to the set of {@link #citizens}.
	 * @param p {@link Player}
	 * @throws Exception
	 */
	public void addCitizen(String s) throws Exception {
		try {
			getCitizens().add(s);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Gets the set of {@link #guilds}.
	 * @return {@link #guilds}
	 */
	public Set<Guild> getGuilds() {
		return guilds;
	}
	/**
	 * Adds a guild to the set of {@link #guilds}.
	 * @param g {@link Guild}
	 * @throws Exception
	 */
	public void addGuild(Guild g) throws Exception {
		try {
			guilds.add(g);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Re-assigns the chat prefix for all citizens of this nation.
	 */
	public void refreshCitizens() {
		for (String st: citizens) {
			Player p = getCore().getServer().getPlayer(st);
			User u = getCore().getUserList().get(st);
			ChatColor rankColor;
			try {
				rankColor = u.getCurrentRank().getColor();
			} catch (Exception e) {
				rankColor = ChatColor.WHITE;
			}
			p.setDisplayName(uniqueColor + "[" + getName() + "] " + rankColor + p.getName() + ChatColor.WHITE);
		}
	}
	
	/**
	 * Constructor, automatically sets the color to {@link ChatColor#DARK_BLUE}.
	 * @param n {@link String}
	 * @param baseCore {@link Core}
	 */
	public Nation(String n, Core baseCore) {
		this.baseCore = baseCore;
		name = n;
		uniqueColor = ChatColor.DARK_BLUE;
	}
	
}