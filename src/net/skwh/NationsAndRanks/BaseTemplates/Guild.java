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
	 * The set of members of this guild.
	 */
	private Set<Player> members = new HashSet<Player>();
	/** 
	 * A map of Players to their ranks.
	 */
	private HashMap<Player, Rank> playerRankList = new HashMap<Player,Rank>();
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
	public HashMap<Player, Rank> getPlayerRankList() {
		return playerRankList;
	}
	
	/**
	 * Sets a given player to a given rank.
	 * @param p {@link Player}
	 * @param r {@link Rank}
	 */
	public void setPlayerToRank(Player p, Rank r) {
		if (members.contains(p)) {
			if (playerRankList.containsKey(p)) {
				playerRankList.remove(p);
			}
			playerRankList.put(p, r);
		}
	}
	
	/**
	 * Gets the rank of a given player.
	 * @param p {@link Player}
	 * @return {@link Rank}
	 * @throws Exception
	 */
	public Rank getRankForPlayer(Player p) throws Exception {
		if (playerRankList.containsKey(p)) {
			return playerRankList.get(p);
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
	public Set<Player> getMembers() {
		return members;
	}
	/**
	 * Adds a given player to the members of this guild.
	 * @param p {@link Player}
	 * @throws Exception
	 */
	public void addMember(Player p) throws Exception {
		try {
			members.add(p);
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
		spawnPoint = n.getCore().getServer().getWorld(n.getCore().getWorldName()).getSpawnLocation();
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
