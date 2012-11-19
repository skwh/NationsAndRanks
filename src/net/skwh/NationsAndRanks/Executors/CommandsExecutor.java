package net.skwh.NationsAndRanks.Executors;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandsExecutor extends Core {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (cmd.getName().equalsIgnoreCase("nar") || cmd.getName().equalsIgnoreCase("NationsAndRanks")) {
				sender.sendMessage(ChatColor.GREEN + "NationsAndRanks v" + baseCore.getVersion() + " by SKWH");
			}
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return true;
		}
		Player playah = (Player) sender;
		if (args.length == 1) { //TODO: Add listener for JoinGuild
			if (cmd.getName().equalsIgnoreCase("SetVerbose") || cmd.getName().equalsIgnoreCase("sv")) {
				boolean set;
				if (args[0] == "true") {
					set = true;
				} else if (args[0] == "false") {
					set = false;
				} else {
					sender.sendMessage(ChatColor.RED + "Please enter true or false.");
					return true;
				}
				baseCore.setVerbosity(set);
			}
			//chat command to join a nation
			if (cmd.getName().equalsIgnoreCase("JoinNation") || cmd.getName().equalsIgnoreCase("jn") || cmd.getName().equalsIgnoreCase("join")) {
				boolean nationExists = baseCore.getNation_NameList().containsKey(args[0]);
				if (nationExists) {
					Nation n = baseCore.getNation_NameList().get(args[0]);
					try {
						n.addCitizen(playah);
					} catch (Exception e) {
						baseCore.log("Error adding player to nation: " + e.getMessage());
					}
				}
			}
		}
		if (args.length == 2) {
			
		}
		return true;
	}
}