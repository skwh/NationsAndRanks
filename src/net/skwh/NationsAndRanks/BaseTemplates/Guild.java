package net.skwh.NationsAndRanks.BaseTemplates;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Guild {
	private final String name;
	private final Nation ownerNation;
	private ChatColor color;
	private Set<Rank> ranks = new HashSet<Rank>();
	private Set<Player> members = new HashSet<Player>();
	private HashMap<Player, Rank> playerRankList = new HashMap<Player,Rank>();
	private Location spawnPoint;
	private Vector<Rank> ranksInOrder = new Vector<Rank>();
	
	public String getName() {
		return name;
	}
	
	public Set<Rank> getRanks() {
		return ranks;
	}
	public void addRank(Rank r) throws Exception {
		try {
			ranks.add(r);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public HashMap<Player, Rank> getPlayerRankList() {
		return playerRankList;
	}
	public void setPlayerToRank(Player p, Rank r) {
		if (playerRankList.containsKey(p)) {
			playerRankList.remove(p);
		}
		playerRankList.put(p, r);
	}
	
	public Rank getRankForPlayer(Player p) throws Exception {
		if (playerRankList.containsKey(p)) {
			return playerRankList.get(p);
		} else {
			throw new Exception("The specified player is not in the ranklist.");
		}
	}
	
	public ChatColor getChatColor() {
		return color;
	}
	public void setChatColor(ChatColor c) {
		this.color = c;
	}
	
	public Nation getOwnerNation() {
		return ownerNation;
	}
	
	public Set<Player> getMembers() {
		return members;
	}
	public void addMember(Player p) throws Exception {
		try {
			members.add(p);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Guild(String name,Nation n) {
		this.name = name;
		this.ownerNation = n;
		spawnPoint = n.getCore().getServer().getWorld(n.getCore().getWorldName()).getSpawnLocation();
	}

	public Location getSpawnPoint() {
		return spawnPoint;
	}

	public void setSpawnPoint(Location spawnPoint) {
		this.spawnPoint = spawnPoint;
	}

	public Vector<Rank> getRanksInOrder() {
		return ranksInOrder;
	}

	public void setRanksInOrder(Vector<Rank> ranksInOrder) {
		this.ranksInOrder = ranksInOrder;
	}
	
	public void addToRanksInOrder(Rank r) {
		ranksInOrder.add(r);
	}
	
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
