package net.skwh.NationsAndRanks.Executors;

import java.util.Set;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Guild;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;
import net.skwh.NationsAndRanks.BaseTemplates.User;
import net.skwh.NationsAndRanks.Utilites.JamesBond;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
/**
 * @author Evan Derby <somekidwithhtml@gmail.com>
 * @version 0.8
 * @since 2012-11-29
 */
public class CommandsExecutor extends Core {
	
	/**
	 * Handles command requests from {@link Core}.
	 * 
	 * @param sender See {@link CommandSender}.
	 * @param cmd See {@link Command}.
	 * @param args An array of arguments provided.
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player playah = (Player) sender;
		String usahName = playah.getName();
		if (args.length == 0) {
			if (cmd.getName().equalsIgnoreCase("nar") || cmd.getName().equalsIgnoreCase("NationsAndRanks")) {
				sender.sendMessage(ChatColor.GREEN + "NationsAndRanks v" + getBaseCore().getVersion() + " by SKWH");
			}
			if (cmd.getName().equalsIgnoreCase("list")) {
				sender.sendMessage(ChatColor.DARK_PURPLE + cmd.getUsage());
			}
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return true;
		}
		if (args.length == 1) {
			if (cmd.getName().equalsIgnoreCase("SetVerbose") || cmd.getName().equalsIgnoreCase("sv")) {
				boolean set;
				if (args[0].equalsIgnoreCase("true")) {
					set = true;
				} else if (args[0].equalsIgnoreCase("false")) {
					set = false;
				} else {
					sender.sendMessage(ChatColor.RED + "Please enter true or false.");
					return true;
				}
				getBaseCore().setVerbosity(set);
			}
			//chat command to join a nation
			if (cmd.getName().equalsIgnoreCase("JoinNation") || cmd.getName().equalsIgnoreCase("jn")) {
				if (!JamesBond.doesPlayerBelongToCountry(usahName)) {
					boolean nationExists = baseCore.getNation_NameList().containsKey(args[0]);
					if (nationExists) {
						Nation n = getBaseCore().getNation_NameList().get(args[0]);
						boolean failure = false;
						try {
							n.addCitizen(usahName);
						} catch (Exception e) {
							failure = true;
							getBaseCore().log("Error adding player to nation: " + e.getMessage());
						} finally {
							if (failure) {
								playah.sendMessage(ChatColor.RED + "Sorry, there was a problem adding you to " + args[0] + ".");
							} else {
								playah.sendMessage(ChatColor.GREEN + "Congradulations, Welcome to " + args[0] + ", " + playah.getDisplayName());
								n.refreshCitizens();
								if (!JamesBond.isPlayerInUserList(usahName)) {
									User u = User.makeUserWithAttributes(playah, n, null, null);
									User.addToUserList(getBaseCore(), u);
								} else {
									getBaseCore().getUserList().get(usahName).setNation(true, n);
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
			//chat command to join a guild
			if (cmd.getName().equalsIgnoreCase("JoinGuild") || cmd.getName().equalsIgnoreCase("jg")) {
				if (JamesBond.doesPlayerBelongToCountry(usahName)) {
					if (!JamesBond.doesPlayerBelongToGuild(usahName)) {
						if (JamesBond.getGuilds().containsKey(args[0])) {
							boolean failed = false;
							Guild g = JamesBond.getGuilds().get(args[0]);
							getBaseCore().log("Ranks for guild: " + JamesBond.rankGetNames(g.getRanks()).toString());
							try {
								g.addMember(usahName);
								g.setPlayerToRank(usahName, g.getRanksInOrder().firstElement());
							} catch (Exception e) {
								failed = true;
								e.printStackTrace();
							} finally {
								if (failed) {
									playah.sendMessage(ChatColor.RED + "Sorry, there was a problem adding you to " + args[0] + ".");
								} else {
									playah.sendMessage(ChatColor.GREEN + "Welcome to " + args[0] + "," + playah.getDisplayName() + "!");
									if (!JamesBond.isPlayerInUserList(usahName)) {
										User u = new User(playah);
										u.setNation(true, JamesBond.getNationForPlayer(u.getUserName()));
										u.setGuild(true, JamesBond.getGuildForPlayer(u.getUserName()));
									} else {
										getBaseCore().getUserList().get(usahName).setGuild(true, JamesBond.getGuildForPlayer(usahName));
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
			//command to get the nations/guilds/ranks (list)
			if (cmd.getName().equalsIgnoreCase("list")) {
				if (args[0].equalsIgnoreCase("nations") || args[0].equalsIgnoreCase("nation")) {
					playah.sendMessage("Nation list: " + getBaseCore().getNationNames().toString());
				} else if (args[0].equalsIgnoreCase("guilds") || args[0].equalsIgnoreCase("guild")) {
					if (JamesBond.doesPlayerBelongToCountry(usahName)) {
						Set<String> guildList = JamesBond.guildGetNames(JamesBond.getNationForPlayer(usahName).getGuilds());
						playah.sendMessage("Guild list: " + guildList.toString());
					} else {
						playah.sendMessage(ChatColor.RED + "You must belong to a nation to get the list of guilds!");
					}
				} else if (args[0].equalsIgnoreCase("ranks") || args[0].equalsIgnoreCase("rank")) {
					if (JamesBond.doesPlayerBelongToGuild(usahName)) {
						Guild g = JamesBond.getGuildForPlayer(usahName);
						if (g == null) {
							playah.sendMessage("There was an error getting the ranks for this guild.");
							return true;
						}
						Set<String> rankList = JamesBond.rankGetNames(g.getRanks());
						playah.sendMessage("Rank List: " + rankList.toString());
					} else {
						playah.sendMessage(ChatColor.RED + "You must belong to a guild to get the list of ranks!");
					}
				}
			}
			// setguildspawn
			if (cmd.getName().equalsIgnoreCase("SetGuildSpawn") || cmd.getName().equalsIgnoreCase("sgs")) {
				
			}
		}
		if (args.length == 2) {
			
		}
		return true;
	}
}