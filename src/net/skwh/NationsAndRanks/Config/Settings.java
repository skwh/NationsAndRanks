package net.skwh.NationsAndRanks.Config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	protected void loadKeys() {
		List<String> l1 = (List<String>) config.getList("Nations");
		basePlugin.log(l1.toString());
		for (int i=0;i<l1.size();i++) {
			NationList.add(new Nation((String) l1.toArray()[i]));
		}
		
	}
}
