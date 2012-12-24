package net.skwh.NationsAndRanks.Config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.bukkit.inventory.ItemStack;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Guild;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;
import net.skwh.NationsAndRanks.BaseTemplates.Rank;
import net.skwh.NationsAndRanks.Utilites.JamesBond;
/**
 * @author Evan Derby <somekidwithhtml@gmail.com>
 * @version 0.8
 * @since 2012-11-29
 */
public class Settings extends ConfigLoader {
	/**
	 * A set of local Nations being parsed by {@link #loadKeys()}.
	 */
	private Set<Nation> NationList = new HashSet<Nation>();
	/**
	 * A set of local Guilds being parsed by {@link #loadKeys()}.
	 */
	private Set<Guild> GuildList = new HashSet<Guild>();
	/**
	 * A set of local Ranks being parsed by {@link #loadKeys()}.
	 */
	private Vector<Rank> RankList = new Vector<Rank>();
	/**
	 * A local copy of the given world name. 
	 */
	private String worldName;
	
	public Settings(Core baseCore,String filename) {
		super(baseCore, filename);
		config = baseCore.getConfig();
		super.saveIfNotExist();
	}

	public void load(){
		if (!configFile.exists()) {
			dataFolder.mkdir();
			basePlugin.saveDefaultConfig();
		}
		super.addDefaults();
		loadKeys();
	}
	/**
	 * Loads the nations, guilds, ranks and settings from the config.yml file.
	 */
	protected void loadKeys() {
		worldName = config.getString("World_name");
		basePlugin.setWorldName(worldName);
		
		List<String> l1 = (List<String>) config.getList("Nations");
		for (String st : l1) {
			NationList.add(new Nation(st,basePlugin));
		}
		for (Nation n : NationList) {
			String color = config.getString(n.getName() + ".Color");
			n.setColor(JamesBond.interpretColor(color));
			List<String> l2 = (List<String>) config.getList(n.getName() + ".Guilds");
			for (String st : l2) {
				Guild currentGuild = new Guild(st,n);
				try {
					n.addGuild(currentGuild);
					GuildList.add(currentGuild);
				} catch (Exception e) {
					basePlugin.log("There was an error adding guild " + currentGuild.getName() + " to nation " + n.getName() + ": " + e.getMessage());
				}
			}
		}
		for (Guild g : GuildList) {
			List<String> l3 = (List<String>) config.getList(g.getOwnerNation().getName() + "." + g.getName() + ".Ranks");
			for (String st : l3) {
				Rank currentRank = new Rank(st,g);
				try {
					RankList.add(currentRank);
					g.addRank(currentRank);
					g.addToRanksInOrderWithUpDown(currentRank);
					basePlugin.log(JamesBond.displayRankHierarchy(g.getRanksInOrder()));
				} catch (Exception e) {
					basePlugin.log("There was an error adding rank " + currentRank.getName() + " to guild " + g.getName() + ":" + e.getMessage());
				}
			}
		}
		for (Rank r: RankList) {
			int price = (int) config.getInt(r.getOwnerGuild().getOwnerNation().getName() + "." + r.getOwnerGuild().getName() + "." + r.getName() + ".Price");
			String color = (String) config.getString(r.getOwnerGuild().getOwnerNation().getName() + "." + r.getOwnerGuild().getName() + "." + r.getName() + ".Color");
			List<Integer> items = (List<Integer>) config.getIntegerList(r.getOwnerGuild().getOwnerNation().getName() + "." + r.getOwnerGuild().getName() + "." + r.getName() + ".Items");
			Set<ItemStack> itemStacks = new HashSet<ItemStack>();
			for (int i : items) {
				ItemStack item = new ItemStack(i);
				itemStacks.add(item);
			}
			try {
				r.setPayRequired(price);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				r.setKit(itemStacks);
			} catch(Exception e) {
				e.printStackTrace();
			}
			try {
				r.setColor(JamesBond.interpretColor(color));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		assignLists();
	}
	/**
	 * Assigns the local copies of the lists to their {@link Core} counterparts.
	 */
	private void assignLists() {
		for (Nation n : NationList) {
			basePlugin.getNationList().add(n);
			basePlugin.getNation_NameList().put(n.getName(),n);
			basePlugin.getNationNames().add(n.getName());
		}
	}
}
