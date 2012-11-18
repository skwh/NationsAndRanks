package net.skwh.NationsAndRanks.BaseTemplates;

import java.util.HashSet;
import java.util.Set;

import net.skwh.NationsAndRanks.Core;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Nation {
	private final String name;
	private String short_name;
	private Borders borders;
	private Set<Player> citizens = new HashSet<Player>();
	private Set<Guild> guilds = new HashSet<Guild>();
	private ChatColor uniqueColor;
	
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
	public ChatColor getColor() {
		return uniqueColor;
	}
	
	public String getName() {
		return name;
	}
	
	public Borders getBorders() {
		return borders;
	}
	public void SetBorders(Set<Block> b) {
		this.borders = new Borders(this,b);
	}
	
	public void setShortName(String s) {
		short_name = s;
	}
	public String getShortName() {
		return short_name;
	}
	
	public Set<Player> getCitizens() {
		return citizens;
	}
	public void addCitizen(Player p) throws Exception {
		try {
			getCitizens().add(p);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Set<Guild> getGuilds() {
		return guilds;
	}
	public void addGuild(Guild g) throws Exception {
		try {
			guilds.add(g);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void refreshCitizens() {
		for (int i=0;i<citizens.toArray().length;i++) {
			Player p = (Player) citizens.toArray()[i];
			p.setDisplayName(uniqueColor + "[" + getName() + "]" + ChatColor.BLACK + p.getName());
		}
	}
	
	public Nation(String n) {
		name = n;
		uniqueColor = ChatColor.DARK_BLUE;
	}
	
}
