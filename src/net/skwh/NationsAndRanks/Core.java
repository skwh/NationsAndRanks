package net.skwh.NationsAndRanks;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import net.skwh.NationsAndRanks.BaseTemplates.Nation;
import net.skwh.NationsAndRanks.Config.FileHandler;
import net.skwh.NationsAndRanks.Config.Settings;
import net.skwh.NationsAndRanks.Executors.CommandsExecutor;
import net.skwh.NationsAndRanks.Listeners.PlayerChatListener;
import net.skwh.NationsAndRanks.Listeners.PlayerJoinListener;
import net.skwh.NationsAndRanks.Utilites.SLAPI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
	public static Core baseCore;
	private static CommandsExecutor CE = new CommandsExecutor();
	private static final FileHandler FE = new FileHandler();
	
	public static Core getBaseCore() {
		return baseCore;
	}
	
	private String version = "0.5";
	private boolean debugging = true;
	private boolean firstTime = true;
	
	public boolean getDebugging() {
		return debugging;
	}
	public void setDebugging (boolean b) {
		debugging = b;
	}
	public String getVersion() {
		return version;
	}
	
	private boolean verbose = true;
	
	public boolean getVerbosity() {
		return verbose;
	}
	public void setVerbosity(boolean b) {
		verbose = b;
	}
	
	protected Set<Nation> NationList = new HashSet<Nation>();
	protected HashMap<String,Nation> Nation_NameList = new HashMap<String,Nation>();
	protected HashMap<Player,String> PlayerReferences = new HashMap<Player,String>();
	protected Set<String> NationNames = new HashSet<String>();
	
	public HashMap<Player,String> getPlayerReferences() {
		return PlayerReferences;
	}
	public HashMap<String,Nation> getNation_NameList() {
		return Nation_NameList;
	}
	public Set<String> getNationNames() {
		return NationNames;
	}
	public Set<Nation> getNationList() {
		return NationList;
	}
	
	public void log(String text) {
		if (debugging) {
			this.getLogger().info(text);
		}
	}
	private void setUpListeners() { //TODO: add PlayerSpawn listener for kit assignment and PVP only thingy
		final Listener[] listeners = {new PlayerJoinListener(),new PlayerChatListener()};
		for (Listener l : listeners) {
			getServer().getPluginManager().registerEvents(l, this);
			log("Registered " + l.getClass().getSimpleName() + "' event methods.");
		}
	}
	public Nation findNationByName(String name) throws Exception {
		if (getBaseCore().getNation_NameList().containsKey(name)) {
			return getBaseCore().getNation_NameList().get(name);
		} else {
			throw new Exception("Nation not found.");
		}
	}
	
	public void onEnable() {
		baseCore = this;
		setUpListeners();
		if (!FE.checkExistingFile()) {
			new Settings(this, "config.yml").load();
		} else {
			firstTime = false;
			FE.assignExistingFile();
		}
	}
	
	public void onDisable() {
		this.getLogger().info("Nations and Ranks Disabled, Thanks for using our plugin!");
		FE.saveCreatedFiles();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return CE.onCommand(sender, cmd, label, args);
	}
}
