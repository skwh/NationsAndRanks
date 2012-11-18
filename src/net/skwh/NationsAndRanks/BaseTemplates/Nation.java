package net.skwh.NationsAndRanks.BaseTemplates;

import java.util.HashSet;
import java.util.Set;

import net.skwh.NationsAndRanks.Core;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Nation {
	private String name;
	private String short_name;
	private Borders borders;
	private Set<Player> citizens = new HashSet<Player>();
	private ChatColor uniqueColor;
	private Player owner;
	
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
	
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player p) {
		owner = p;
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
	
	public void refreshCitizens() {
		for (int i=0;i<citizens.toArray().length;i++) {
			Player p = (Player) citizens.toArray()[i]; 
			if (p == this.owner) {
				p.setDisplayName(uniqueColor + "[" + getName() + "]" + ChatColor.AQUA + p.getName());
			} else {
				p.setDisplayName(uniqueColor + "[" + getName() + "]" + ChatColor.BLACK + p.getName());
			}
		}
	}
	
	public Nation(String n,Player owner) {
		name = n;
		this.owner = owner;
		uniqueColor = ChatColor.DARK_BLUE;
	}
	
}
