package net.skwh.NationsAndRanks.Config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.inventory.ItemStack;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Guild;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;
import net.skwh.NationsAndRanks.BaseTemplates.Rank;

public class Settings extends ConfigLoader {
	private Set<Nation> NationList = new HashSet<Nation>();
	private Set<Guild> GuildList = new HashSet<Guild>();
	private Set<Rank> RankList = new HashSet<Rank>();
	
	public Settings(Core baseCore,String filename) {
		super(baseCore, filename);
		config = baseCore.getConfig();
		super.saveIfNotExist();
	}

	public void load() {
		if (!configFile.exists()) {
			dataFolder.mkdir();
			basePlugin.saveDefaultConfig();
		}
		super.addDefaults();
		loadKeys();
	}
	
	@SuppressWarnings("unchecked")
	protected void loadKeys() {
		List<String> l1 = (List<String>) config.getList("Nations"); //get nations
		basePlugin.log(l1.toString());
		for (int i=0;i<l1.size();i++) { //create each nation object
			NationList.add(new Nation((String) l1.toArray()[i]));
		}
		for (int i=0;i<NationList.size();i++) { //for each nation
			String CurrentNationName = ((Nation)NationList.toArray()[i]).getName();
			List<String> l2 = (List<String>) config.getList( CurrentNationName + ".Guilds"); //find the guilds
			for (int j=0;j<l2.size();j++) { //for each guild
				GuildList.add(new Guild((String)l2.toArray()[j],(Nation)NationList.toArray()[j])); //create the guild object
				String CurrentGuildName = ((Guild) GuildList.toArray()[j]).getName();
				List<String> l3 = (List<String>) config.getList(CurrentNationName + "." + CurrentGuildName + ".Ranks"); //find the ranks
				for (int k=0;k<l3.size();j++) { //for each rank
					RankList.add(new Rank((String) l3.toArray()[k],(Guild)GuildList.toArray()[k])); //create the rank object
					String CurrentRankName = ((Rank) RankList.toArray()[k]).getName();
					
					int price = config.getInt(CurrentNationName + "." + CurrentGuildName + "." + CurrentRankName + ".Price");
					List<Integer> items = (List<Integer>) config.getList(CurrentNationName + "." + CurrentGuildName + "." + CurrentRankName + ".Items");
					((Rank) RankList.toArray()[k]).setPayRequired(price);
					for (int x=0;x<items.size();x++) { //for each item
						((Rank) RankList.toArray()[k]).addToKit(new ItemStack((Integer) items.toArray()[x])); //create a new itemstack with that value
					}
				}
				try {
					((Guild)GuildList.toArray()[j]).addRank((Rank)RankList.toArray()[j]);
				} catch (Exception e) {
					basePlugin.log("There was a problem adding rank to guilds: " + e.getMessage());
				}
			}
			try {
				((Nation)NationList.toArray()[i]).addGuild((Guild)GuildList.toArray()[i]);
			} catch (Exception e) {
				basePlugin.log("There was a problem setting guilds: " + e.getMessage());
			}
		}
		assignLists();
	}
	private void assignLists() {
		for (int i=0;i<NationList.size();i++) {
			Nation nationAtIndex = (Nation) NationList.toArray()[i];
			basePlugin.getNationList().add((Nation) NationList.toArray()[i]);
			basePlugin.getNation_NameList().put(nationAtIndex.getName(),nationAtIndex);
			basePlugin.getNationNames().add(nationAtIndex.getName());
		}
	}
}
