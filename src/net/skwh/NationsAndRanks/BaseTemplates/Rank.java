package net.skwh.NationsAndRanks.BaseTemplates;

import java.util.Set;

import org.bukkit.inventory.ItemStack;
/**
 * The template for creating a Rank.
 * @author Evan Derby <somekidwithhtml@gmail.com>
 * @version 0.8
 * @since 2012-11-29
 */
public class Rank {
	/**
	 * The name of this rank.
	 */
	private final String name;
	/**
	 * The guild that owns this rank.
	 */
	private final Guild ownerGuild;
	/**
	 * The rank that is above this one.
	 * <br>
	 * If this is the top rank, up will be reflexive.
	 */
	private Rank up;
	/**
	 * The rank that is below this one.
	 * <br>
	 * If this is the bottom rank, down will be reflexive.
	 */
	private Rank down;
	/**
	 * The kit that this rank receives on spawn.
	 */
	private Set<ItemStack> kit;
	/**
	 * The amount of money required to level up.
	 */
	private int payRequired;
	
	/**
	 * Returns the up rank.
	 * @return {@link #up}
	 */
	public Rank getUp() {
		return up;
	}
	/**
	 * Sets the up rank.
	 * @param r {@link Rank}
	 */
	public void setUp(Rank r) {
		this.up = r;
	}
	/**
	 * Returns the down rank.
	 * @return {@link #down}
	 */
	public Rank getDown() {
		return down;
	}
	/**
	 * Sets the down rank.
	 * @param r {@link Rank}
	 */
	public void setDown(Rank r) {
		this.down=r;
	}
	/**
	 * Returns the name of this guild.
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns the owner guild of this rank.
	 * @return {@link #ownerGuild}
	 */
	public Guild getOwnerGuild() {
		return ownerGuild;
	}
	/**
	 * Returns the pay required to level up.
	 * @return {@link #payRequired}
	 */
	public int getPayRequired() {
		return payRequired;
	}
	/** 
	 * sets the pay required to level up.
	 * @param i {@link Integer}
	 */
	public void setPayRequired(int i) {
		payRequired = i;
	}
	/**
	 * Returns the {@link #kit}.
	 * @return {@link #kit}
	 */
	public Set<ItemStack> getKit() {
		return kit;
	}
	/**
	 * Sets the kit.
	 * @param kit {@link Set}
	 */
	public void setKit(Set<ItemStack> kit) {
		this.kit = kit;
	}
	/**
	 * Adds Items to the kit.
	 * @param Items {@link ItemStack}
	 * @throws Exception
	 */
	public void addToKit(ItemStack Items) throws Exception {
		try {
			kit.add(Items);
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * Full Constructor 
	 * @param u {@link Rank} Up rank.
	 * @param d {@link Rank} Down rank.
	 * @param n {@link String} Name.
	 * @param g {@link Guild} Owner guild.
	 */
	public Rank(Rank u,Rank d,String n,Guild g) {
		name = n;
		up = u;
		down = d;
		ownerGuild = g;
	}
	/**
	 * Secondary Constructor (Simpler)
	 * @param name {@link String}
	 * @param g {@link Guild} Owner guild.
	 */
	public Rank(String name, Guild g) {
		this.name = name;
		this.ownerGuild = g;
	}
}
