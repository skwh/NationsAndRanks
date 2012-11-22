package net.skwh.NationsAndRanks.Executors;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Guild;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;
import net.skwh.NationsAndRanks.BaseTemplates.User;
import net.skwh.NationsAndRanks.Utilites.JamesBond;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandsExecutor extends Core {
	private static final JamesBond DoubleOhSeven = new JamesBond();
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return true;
		}
		Player playah = (Player) sender;
		if (args.length == 0) {
			if (cmd.getName().equalsIgnoreCase("nar") || cmd.getName().equalsIgnoreCase("NationsAndRanks")) {
				sender.sendMessage(ChatColor.GREEN + "NationsAndRanks v" + getBaseCore().getVersion() + " by SKWH");
			}
			if (cmd.getName().equalsIgnoreCase("getkit") || cmd.getName().equalsIgnoreCase("gk")) {
				if (DoubleOhSeven.doesPlayerBelongToGuild(playah)) {
					
				} else {
					playah.sendMessage("You must belong to a guild to get a kit!");
				}
			}
		}
		if (args.length == 1) {
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
			if (cmd.getName().equalsIgnoreCase("JoinNation") || cmd.getName().equalsIgnoreCase("jn")) {
				String formattedArgs = args[0].toLowerCase();
				if (!DoubleOhSeven.doesPlayerBelongToCountry(playah)) {
					boolean nationExists = baseCore.getNation_NameList().containsKey(formattedArgs);
					if (nationExists) {
						Nation n = getBaseCore().getNation_NameList().get(formattedArgs);
						boolean failure = false;
						try {
							n.addCitizen(playah);
						} catch (Exception e) {
							failure = true;
							getBaseCore().log("Error adding player to nation: " + e.getMessage());
						} finally {
							if (failure) {
								playah.sendMessage(ChatColor.RED + "Sorry, there was a problem adding you to " + formattedArgs + ".");
							} else {
								playah.sendMessage(ChatColor.GREEN + "Congradulations, Welcome to " + formattedArgs + ", " + playah.getDisplayName());
								n.refreshCitizens();
								if (!DoubleOhSeven.isPlayerInUserList(playah)) {
									User u = User.makeUserWithAttributes(playah, n, null, null);
									User.addToUserList(getBaseCore(), u);
								} else {
									getBaseCore().getUserList().get(playah).setNation(true, n);
								}
							}
						}
					} else {
						playah.sendMessage("That nation does not exist!");
					}
				} else {
					playah.sendMessage("You are already a citizen of a nation!");
				}
			}
			if (cmd.getName().equalsIgnoreCase("JoinGuild") || cmd.getName().equalsIgnoreCase("jg")) {
				String formattedArgs = args[0].toLowerCase();
				if (DoubleOhSeven.doesPlayerBelongToCountry(playah)) {
					if (!DoubleOhSeven.doesPlayerBelongToGuild(playah)) {
						if (DoubleOhSeven.getGuilds().containsKey(formattedArgs) || DoubleOhSeven.getGuilds().containsKey(args[0])) {
							boolean failed = false;
							try {
								Guild g = DoubleOhSeven.getGuilds().get(formattedArgs);
								g.addMember(playah);
							} catch (Exception e) {
								failed = true;
								getBaseCore().log("There was a problem adding player " + playah.getDisplayName() + " to a guild: " + e.getMessage());
							} finally {
								if (failed) {
									playah.sendMessage(ChatColor.RED + "Sorry, there was a problem adding you to " + formattedArgs + ".");
								} else {
									playah.sendMessage(ChatColor.GREEN + "Welcome to " + formattedArgs + "," + playah.getDisplayName() + "!");
									if (!DoubleOhSeven.isPlayerInUserList(playah)) {
										User u = new User(playah);
										u.setNation(true, DoubleOhSeven.getNationForPlayer(u.getPlayer()));
										u.setGuild(true, DoubleOhSeven.getGuildForPlayer(u.getPlayer()));
									}
								}
							}
						} else {
							playah.sendMessage("No guild with that name exists!");
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