package net.skwh.NationsAndRanks.BaseTemplates;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;

public class Guild {
	private final String name;
	private final Nation ownerNation;
	private ChatColor color;
	private Set<Rank> ranks = new HashSet<Rank>();
	
	public String getName() {
		return name;
	}
	
	public Set<Rank> getRanks() {
		return ranks;
	}
	public void addRank(Rank r) {
		ranks.add(r);
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
	
	public Guild(String name,Set<Rank> ranks,Nation n) {
		this.name = name;
		this.ranks = ranks;
		this.ownerNation = n;
	}
}
