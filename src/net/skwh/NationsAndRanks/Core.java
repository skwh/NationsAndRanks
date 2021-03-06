package net.skwh.NationsAndRanks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import net.skwh.NationsAndRanks.BaseTemplates.Nation;
import net.skwh.NationsAndRanks.BaseTemplates.User;
import net.skwh.NationsAndRanks.Config.FileHandler;
import net.skwh.NationsAndRanks.Config.Settings;
import net.skwh.NationsAndRanks.Executors.CommandsExecutor;
import net.skwh.NationsAndRanks.Listeners.EntityDamageByEntityListener;
import net.skwh.NationsAndRanks.Listeners.PlayerJoinListener;
import net.skwh.NationsAndRanks.Listeners.PlayerRespawnListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
	public static Core baseCore;
	
	public static Core getBaseCore() {
		return baseCore;
	}
	
	private static CommandsExecutor CE = new CommandsExecutor();
	private static final FileHandler FE = new FileHandler();
	
	private String version = "0.8";
	private boolean debugging = true;
	private boolean firstTime = false;
	
	public boolean getDebugging() {
		return debugging;
	}
	public void setDebugging (boolean b) {
		debugging = b;
	}
	public String getVersion() {
		return version;
	}
	
	protected String defaultWorldName;
	protected HashMap<String,User> UserList = new HashMap<String,User>();
	protected Set<Nation> NationList = new HashSet<Nation>();
	protected HashMap<String,Nation> Nation_NameList = new HashMap<String,Nation>();
	protected Set<String> NationNames = new HashSet<String>();
	
	public HashMap<String,User> getUserList() {
		return UserList;
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
	
	protected void setUserList(HashMap<String,User> ul) {
		UserList = ul;
	}
	protected void setNation_NameList(HashMap<String,Nation> nnl) {
		Nation_NameList = nnl;
	}
	protected void setNationList(Set<Nation> n) {
		NationList = n;
	}
	protected void setNationNames(Set<String> n) {
		NationNames = n;
	}
	
	public String getWorldName() {
		return defaultWorldName;
	}
	public void setWorldName(String s) {
		defaultWorldName = s;
	}
	
	public void log(String text) {
		if (debugging) {
			this.getLogger().info(text);
		}
	}
	private void setUpListeners() { //TODO: add PVP only thingy
		final Listener[] listeners = {new PlayerJoinListener(), new EntityDamageByEntityListener(), new PlayerRespawnListener()};
		for (Listener l : listeners) {
			getServer().getPluginManager().registerEvents(l, this);
			log("Registered " + l.getClass().getSimpleName() + "'s event methods.");
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
		//if (!FE.checkExistingFile()) {
			new Settings(this, "config.yml").load();
		//	firstTime = true;
		//} else {
		//	FE.assignExistingFile();
		//}
	}
	
	public void onDisable() {
		/*if (firstTime) {
			FE.createNewFiles();
		}
		FE.saveCreatedFiles();*/
		this.getLogger().info("Nations and Ranks Disabled, Thanks for using the plugin!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return CE.onCommand(sender, cmd, label, args);
	}
}
