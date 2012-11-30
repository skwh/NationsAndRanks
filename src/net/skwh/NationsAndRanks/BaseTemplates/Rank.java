package net.skwh.NationsAndRanks.BaseTemplates;

import java.util.Set;

import org.bukkit.inventory.ItemStack;

public class Rank {
	private final String name;
	private final Guild ownerGuild;
	private Rank up;
	private Rank down;
	private Set<ItemStack> kit;
	private int payRequired;
	
	public Rank getUp() {
		return up;
	}
	public void setUp(Rank r) {
		this.up = r;
	}
	public Rank getDown() {
		return down;
	}
	public void setDown(Rank r) {
		this.down=r;
	}
	
	public String getName() {
		return name;
	}

	public Guild getOwnerGuild() {
		return ownerGuild;
	}
	
	public int getPayRequired() {
		return payRequired;
	}
	public void setPayRequired(int i) {
		payRequired = i;
	}
	
	public Set<ItemStack> getKit() {
		return kit;
	}
	public void setKit(Set<ItemStack> kit) {
		this.kit = kit;
	}
	public void addToKit(ItemStack Items) throws Exception {
		try {
			kit.add(Items);
		} catch (Exception e) {
			throw e;
		}
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
