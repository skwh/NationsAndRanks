package net.skwh.NationsAndRanks.BaseTemplates;

import java.util.Vector;

import net.skwh.NationsAndRanks.Core;
import org.bukkit.entity.Player;
/**
 * A class used to represent a player, with additional methods and properties. 
 * @author Evan Derby <somekidwithhtml@gmail.com>
 * @version 0.8
 * @since 2012-11-29
 */
public class User {
	/**
	 * Adds the given user to the user list in the given {@link Core}.
	 * @param basePlugin {@link Core}
	 * @param u {@link User}
	 */
	public static void addToUserList(Core basePlugin, User u) {
		basePlugin.getUserList().put(u.pl, u);
	}
	/**
	 * Creates a new user with the attributes given and returns it.
	 * @param p {@link Player}
	 * @param n {@link Nation}
	 * @param g {@link Guild}
	 * @param r {@link Rank}
	 * @return {@link User}
	 */
	public static User makeUserWithAttributes(Player p,Nation n, Guild g, Rank r) {
		if (p == null) {
			return null;
		}
		User u = new User(p);
		if (n != null) {
			u.setNation(true, n);
			if (g != null) {
				u.setGuild(true, g);
				if (r != null) {
					u.setCurrentRank(r);
				}
			}
		}
		return u;
	}
	
	/**
	 * The Player's Username that this user references.
	 */
	private String pl;
	/**
	 * The Player that this user references.
	 */
	private Player p;
	/**
	 * Whether or not this user belongs to a {@link Nation}.
	 */
	private boolean belongsToNation = false;
	/**
	 * The {@link Nation} that this user might belong to.
	 */
	private Nation ownerNation = null;
	
	/**
	 * Whether or not this user belongs to a {@link Guild}.
	 */
	private boolean belongsToGuild = false;
	/**
	 * The {@link Guild} that this player might belong to.
	 */
	private Guild ownerGuild = null;
	
	/**
	 * The {@link Rank} that this player might have.
	 */
	private Rank currentRank = null;
	
	/**
	 * Returns the {@link #currentRank}.
	 * @return {@link Rank}
	 */
	public Rank getCurrentRank() {
		return currentRank;
	}
	/**
	 * Sets the current rank.
	 * @param r {@link Rank}
	 */
	public void setCurrentRank(Rank r) {
		currentRank = r;
	}
	/**
	 * Sets the current rank, and sets the player to that rank in the guild's player-rank list. ({@link Guild#getPlayerRankList()})
	 * @param r
	 */
	public void setCurrentRankGuild(Rank r) {
		currentRank = r;
		ownerGuild.setPlayerToRank(this.pl,r);
	}
	/**
	 * Returns if the player belongs to a nation.
	 * @return {@link Boolean}
	 */
	public boolean doesBelongToNation() {
		return belongsToNation;
	}
	/**
	 * Returns the nation object that this player might belong to.
	 * @return {@link Nation}
	 */
	public Nation getNation() {
		return ownerNation;
	}
	/**
	 * Sets the nation that the player belongs to, and changes {@link #belongsToNation} correspondingly.
	 * @param b {@link Boolean}
	 * @param n {@link Nation}
	 */
	public void setNation(Boolean b,Nation n) {
		belongsToNation = b;
		ownerNation = n;
	}
	
	/**
	 * Returns if the player belongs to a guild.
	 * @return {@link Boolean}
	 */
	public boolean doesBelongToGuild() {
		return belongsToGuild;
	}
	/**
	 * Returns the guild that the player might belong to.
	 * @return {@link Guild}
	 */
	public Guild getGuild() {
		return ownerGuild;
	}
	/**
	 * Sets the guild that the player belongs to, and changes {@link #belongsToGuild} at the same time.
	 * @param b {@link Boolean}
	 * @param g {@link Guild}
	 */
	public void setGuild(Boolean b, Guild g) {
		belongsToGuild = b;
		ownerGuild = g;
	}
	
	/**
	 * Returns the player that this user references.
	 */
	public Player getPlayer() {
		return p;
	}
	/**
	 * Returns the player's username that this user references.
	 */
	public String getUserName() {
		return pl;
	}
	
	/**
	 * Sets the user's rank to the next highest in the list.
	 */
	public void RankUp() {
		Vector<Rank> ranksInOrder = ownerGuild.getRanksInOrder();
		for (Rank r : ranksInOrder) {
			ownerNation.getCore().log("Rank " + r.getName() + " Up: " + r.getUp().getName() + " Down: " + r.getDown().getName());
			if (r == currentRank) {
				if (r.getUp() != null) {
					currentRank = r.getUp();
				}
			}
		}
	}
	
	/**
	 * Sets the user's rank to the next lowest in the list.
	 */
	public void RankDown() {
		Vector<Rank> ranksInOrder = ownerGuild.getRanksInOrder();
		for (Rank r : ranksInOrder) {
			ownerNation.getCore().log("Rank " + r.getName());
			if (r == currentRank) {
				if (r.getDown() != null) {
					currentRank = r.getDown();
				}
			}
		}
	}
	
	/**
	 * Basic constructor.
	 * @param p {@link Player}
	 */
	public User(Player p) {
		this.p = p;
		this.pl = p.getName();
	}
}