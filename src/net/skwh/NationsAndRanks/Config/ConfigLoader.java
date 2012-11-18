package net.skwh.NationsAndRanks.Config;
/*
	Written by MilkyWayZ.
*/
import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.skwh.NationsAndRanks.Core;

public abstract class ConfigLoader {
	protected String fileName;
	protected File configFile;
	protected File configFolder;
	protected final Core basePlugin;
	private File dataFolder;
	protected static FileConfiguration config;
	
	public ConfigLoader(Core plugin, String fileName) {
		this.basePlugin = plugin;
		this.fileName = fileName;
		this.dataFolder = plugin.getDataFolder();
		this.configFile = new File(this.dataFolder, File.separator + fileName);
		config = YamlConfiguration.loadConfiguration(this.configFile);
	}
	
	public void load() {
		if (!this.configFile.exists()) {
			this.dataFolder.mkdir();
			saveConfig();
		}
		addDefaults();
		loadKeys();
	}
	
	protected void saveConfig() {
		try {
			config.save(this.configFile);
		} catch (IOException ex) {
		}
	}
	protected void saveIfNotExist() {
		if (!this.configFile.exists()) {
			if (this.basePlugin.getResource(this.fileName) != null) {
				this.basePlugin.saveResource(this.fileName, false);
			}
		}
		rereadFromDisk();
	}
	protected void rereadFromDisk() {
		config = YamlConfiguration.loadConfiguration(this.configFile);
	}
	protected void addDefaults() {
		config.options().copyDefaults(true);
		saveConfig();
	}
	protected abstract void loadKeys();
}
