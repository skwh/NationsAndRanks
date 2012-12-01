package net.skwh.NationsAndRanks.BaseTemplates;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
/**
 * The template for creating a guild.
 * @author Evan Derby <somekidwithhtml@gmail.com>
 * @version 0.8
 * @since 2012-11-29
 */
public class Guild {
	/**
	 * The name of the Guild.
	 */
	private final String name;
	/**
	 * The nation that this Guild belongs to.
	 */
	private final Nation ownerNation;
	/**
	 * The chat color for this guild.
	 */
	private ChatColor color;
	/**
	 * The set of Ranks that are available to players in this guild.
	 */
	private Set<Rank> ranks = new HashSet<Rank>();
	/**
	 * The set of members' usernames of this guild.
	 */
	private Set<String> members = new HashSet<String>();
	/** 
	 * A map of Players to their ranks.
	 */
	private HashMap<String, Rank> playerRankList = new HashMap<String,Rank>();
	/**
	 * The spawn point for this guild.
	 */
	private Location spawnPoint;
	/**
	 * The ranks, in order, as they are avaliable to players.
	 */
	private Vector<Rank> ranksInOrder = new Vector<Rank>();
	
	/**
	 * Returns the name of this guild.
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the set of ranks for this guild.
	 * @return {@link #ranks}
	 */
	public Set<Rank> getRanks() {
		return ranks;
	}
	/**
	 * Adds a {@link Rank} to the list of {@link #ranks}.
	 * @param r {@link Rank}
	 * @throws Exception
	 */
	public void addRank(Rank r) throws Exception {
		try {
			ranks.add(r);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Returns the map of players to their ranks ({@link #playerRankList}).
	 * @return {@link #playerRankList}
	 */
	public HashMap<String, Rank> getPlayerRankList() {
		return playerRankList;
	}
	
	/**
	 * Sets a given player to a given rank.
	 * @param p {@link Player}
	 * @param r {@link Rank}
	 */
	public void setPlayerToRank(String s, Rank r) {
		if (members.contains(s)) {
			if (playerRankList.containsKey(s)) {
				playerRankList.remove(s);
			}
			playerRankList.put(s, r);
		}
	}
	
	/**
	 * Gets the rank of a given player.
	 * @param p {@link Player}
	 * @return {@link Rank}
	 * @throws Exception
	 */
	public Rank getRankForPlayer(String s) throws Exception {
		if (playerRankList.containsKey(s)) {
			return playerRankList.get(s);
		} else {
			throw new Exception("The specified player is not in the ranklist.");
		}
	}
	
	/**
	 * Returns the chat color for this guild.
	 * @return {@link #color}
	 */
	public ChatColor getChatColor() {
		return color;
	}
	/**
	 * Sets the chat color for this guild.
	 * @param c {@link ChatColor}
	 */
	public void setChatColor(ChatColor c) {
		this.color = c;
	}
	
	/**
	 * Returns the owner nation for this guild.
	 * @return {@link #ownerNation}
	 */
	public Nation getOwnerNation() {
		return ownerNation;
	}
	
	/**
	 * Returns the set of members for this guild.
	 * @return {@link #members}
	 */ 
	public Set<String> getMembers() {
		return members;
	}
	/**
	 * Adds a given player to the members of this guild.
	 * @param p {@link Player}
	 * @throws Exception
	 */
	public void addMember(String s) throws Exception {
		try {
			members.add(s);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Constructor, automatically assigns the guild's spawnpoint to that of the default world's default spawn point.
	 * @param name {@link String}
	 * @param n {@link Nation}
	 */
	public Guild(String name,Nation n) {
		this.name = name;
		this.ownerNation = n;
		this.spawnPoint = n.getCore().getServer().getWorld(n.getCore().getWorldName()).getSpawnLocation();
	}
	
	/**
	 * Returns the spawn point for this guild.
	 * @return {@link #spawnPoint}
	 */
	public Location getSpawnPoint() {
		return spawnPoint;
	}
	/**
	 * Sets the spawn point for this guild.
	 * @param spawnPoint {@link Location}
	 */
	public void setSpawnPoint(Location spawnPoint) {
		this.spawnPoint = spawnPoint;
	}

	/**
	 * Returns the ranks in order.
	 * @return {@link #ranksInOrder}
	 */
	public Vector<Rank> getRanksInOrder() {
		return ranksInOrder;
	}
	/**
	 * Sets the ranks in order.
	 * @param ranksInOrder {@link Vector}<Rank>
	 */
	public void setRanksInOrder(Vector<Rank> ranksInOrder) {
		this.ranksInOrder = ranksInOrder;
	}
	
	/**
	 * Adds a rank with no other operations to the ranks in order.
	 * @param r {@link Rank}
	 */
	public void addToRanksInOrder(Rank r) {
		ranksInOrder.add(r);
	}
	
	/**
	 * Adds a rank to the ranks in order, also setting the rank's up rank and down rank in the process.
	 * @param r {@link Rank}
	 */
	public void addToRanksInOrderWithUpDown(Rank r) {
		ranksInOrder.add(r);
		if (ranksInOrder.size() != 1) {
			Rank ru, rd;
			try {
				ru = ranksInOrder.elementAt(ranksInOrder.indexOf(r)+1);
			} catch (ArrayIndexOutOfBoundsException e) {
				ru = r;
			}
			try {
				rd = ranksInOrder.elementAt(ranksInOrder.indexOf(r)-1);
			} catch (ArrayIndexOutOfBoundsException e) {
				rd = r;
			}
			r.setUp(ru);
			r.setDown(rd);
			ru.setDown(r);
			rd.setUp(r);
		}
	}
}
