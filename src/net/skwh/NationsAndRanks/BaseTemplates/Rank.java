package net.skwh.NationsAndRanks.BaseTemplates;

import org.bukkit.inventory.Inventory;

public class Rank {
	public final String name;
	private Rank up;
	private Rank down;
	private Inventory kit;
	private final int payRequired;
	
	public Rank getUp() {
		return up;
	}
	public Rank getDown() {
		return down;
	}
	
	public int getPayRequired() {
		return payRequired;
	}
	
	public Inventory getKit() {
		return kit;
	}
	public void setKit(Inventory kit) {
		this.kit = kit;
	}
	
	public Rank(Rank u,Rank d,String n,int pR) {
		name = n;
		up = u;
		down = d;
		payRequired = pR;
	}
}
