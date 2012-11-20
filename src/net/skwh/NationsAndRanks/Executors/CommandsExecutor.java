package net.skwh.NationsAndRanks.Executors;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;
import net.skwh.NationsAndRanks.Utilites.JamesBond;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandsExecutor extends Core {
	private static final JamesBond DoubleOhSeven = new JamesBond();
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (cmd.getName().equalsIgnoreCase("nar") || cmd.getName().equalsIgnoreCase("NationsAndRanks")) {
				sender.sendMessage(ChatColor.GREEN + "NationsAndRanks v" + getBaseCore().getVersion() + " by SKWH");
			}
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return true;
		}
		Player playah = (Player) sender;
		if (args.length == 1) { //TODO: finish listener for JoinGuild
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
				getBaseCore().setVerbosity(set);
			}
			//chat command to join a nation
			if (cmd.getName().equalsIgnoreCase("JoinNation") || cmd.getName().equalsIgnoreCase("jn") || cmd.getName().equalsIgnoreCase("join")) {
				if (!DoubleOhSeven.doesPlayerBelongToCountry(playah)) {
					boolean nationExists = baseCore.getNation_NameList().containsKey(args[0]);
					if (nationExists) {
						Nation n = getBaseCore().getNation_NameList().get(args[0]);
						boolean failure = false;
						try {
							n.addCitizen(playah);
						} catch (Exception e) {
							failure = true;
							getBaseCore().log("Error adding player to nation: " + e.getMessage());
						} finally {
							if (failure) {
								playah.sendMessage(ChatColor.RED + "Sorry, there was a problem adding you to " + args[0] + ".");
							} else {
								playah.sendMessage(ChatColor.GREEN + "Congradulations, Welcome to " + args[0] + ", " + playah.getDisplayName());
								n.refreshCitizens();
							}
						}
					}
				} else {
					playah.sendMessage("You are already a citizen of a nation!");
				}
			}
			if (cmd.getName().equalsIgnoreCase("JoinGuild") || cmd.getName().equalsIgnoreCase("jg")) {
				if (DoubleOhSeven.doesPlayerBelongToCountry(playah)) {
					if (!DoubleOhSeven.doesPlayerBelongToGuild(playah)) {
						boolean failed = false;
						try {
							Nation n = getBaseCore().findNationByName(args[0]);
							n.addCitizen(playah);
						} catch (Exception e) {
							failed = true;
							getBaseCore().log("There was a problem adding player " + playah.getDisplayName() + " to a guild: " + e.getMessage());
						} finally {
							if (failed) {
								playah.sendMessage(ChatColor.RED + "Sorry, there was a problem adding you to this nation.");
							} else {
								playah.sendMessage(ChatColor.GREEN + "Welcome to this nation," + playah.getDisplayName() + "!");
							}
						}
					} else {
						playah.sendMessage("You are already in a guild!");
					}
				} else {
					playah.sendMessage("You must belong to a nation to join a guild!");
				}
			}
		}
		if (args.length == 2) {
			
		}
		return true;
	}
}