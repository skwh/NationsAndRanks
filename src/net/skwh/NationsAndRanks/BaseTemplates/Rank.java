package net.skwh.NationsAndRanks.BaseTemplates;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Rank {
	public final String name;
	public final Guild ownerGuild;
	private Rank up;
	private Rank down;
	private Inventory kit;
	private int payRequired;
	
	public Rank getUp() {
		return up;
	}
	public Rank getDown() {
		return down;
	}
	
	public int getPayRequired() {
		return payRequired;
	}
	public void setPayRequired(int i) {
		payRequired = i;
	}
	
	public Inventory getKit() {
		return kit;
	}
	public void setKit(Inventory kit) {
		this.kit = kit;
	}
	public void addToKit(ItemStack Items) throws Exception {
		try {
			kit.addItem(Items);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Rank(Rank u,Rank d,String n,Guild g) {
		name = n;
		up = u;
		down = d;
		ownerGuild = g;
	}
	public Rank(String name, Guild g) {
		this.name = name;
		this.ownerGuild = g;
	}
}
