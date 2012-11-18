package net.skwh.NationsAndRanks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import net.skwh.NationsAndRanks.BaseTemplates.Nation;
import net.skwh.NationsAndRanks.Executors.CommandsExecutor;
import net.skwh.NationsAndRanks.Listeners.PlayerChatListener;
import net.skwh.NationsAndRanks.Listeners.PlayerJoinListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
	public static Core baseCore;
	private static CommandsExecutor CE = new CommandsExecutor();
	
	public static Core getBaseCore() {
		return baseCore;
	}
	
	private String version = "0.5";
	private boolean debugging = true;
	
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
	
	private HashMap<String,Nation> Nation_OwnerList = new HashMap<String,Nation>();
	private HashMap<String,Nation> Nation_NameList = new HashMap<String,Nation>();
	private HashMap<Player,String> PlayerReferences = new HashMap<Player,String>();
	private Set<String> NationNames = new HashSet<String>();
	
	public HashMap<Player,String> getPlayerReferences() {
		return PlayerReferences;
	}
	public HashMap<String,Nation> getNation_OwnerList() {
		return Nation_OwnerList;
	}
	public HashMap<String,Nation> getNation_NameList() {
		return Nation_NameList;
	}
	public Set<String> getNationNames() {
		return NationNames;
	}
	
	public void log(String text) {
		if (debugging) {
			this.getLogger().info(text);
		}
	}
	private void setUpListeners() {
		final Listener[] listeners = {new PlayerJoinListener(),new PlayerChatListener()};
		for (Listener l : listeners) {
			getServer().getPluginManager().registerEvents(l, this);
			log("Registered " + l.getClass().getSimpleName() + "' event methods.");
		}
	}
	
	public void onEnable() {
		baseCore = this;
		setUpListeners();
	}
	
	public void onDisable() {
		this.getLogger().info("Nations and Ranks Disabled, Thanks for using our plugin!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return CE.onCommand(sender, cmd, label, args);
	}
}
