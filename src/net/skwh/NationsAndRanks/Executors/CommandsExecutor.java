package net.skwh.NationsAndRanks.Executors;

import java.util.Set;
import java.util.Vector;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.BaseTemplates.Guild;
import net.skwh.NationsAndRanks.BaseTemplates.Nation;
import net.skwh.NationsAndRanks.BaseTemplates.User;
import net.skwh.NationsAndRanks.Utilites.JamesBond;

import org.bukkit.ChatColor;
import org.bukkit.Location;
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
			if (cmd.getName().equalsIgnoreCase("getrank") || cmd.getName().equalsIgnoreCase("gr")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
					return true;
				}
				User u = getBaseCore().getUserList().get(usahName);
				if (u.doesBelongToGuild()) {
					displayMessageToPlayer(playah, "Current Guild: " + u.getGuild().getName() + ", Current rank: " + u.getCurrentRank().getName());
				} else if (u.doesBelongToNation()) {
					displayMessageToPlayer(playah, "Current Nation: " + u.getNation().getName() + ", You do not belong to a guild yet.");
				} else {
					displayMessageToPlayer(playah, "You do not belong to a nation yet!");
				}
			}
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return true;
		}
		if (args.length == 1) {
			//chat command to join a nation
			if (cmd.getName().equalsIgnoreCase("JoinNation") || cmd.getName().equalsIgnoreCase("jn")) {
				if (!JamesBond.doesPlayerBelongToCountry(usahName)) {
					boolean nationExists = baseCore.getNation_NameList().containsKey(args[0]);
					if (nationExists) {
						Nation n = getBaseCore().getNation_NameList().get(args[0]);
						boolean failure = false;
						try {
							n.addCitizen(usahName);
							getBaseCore().getUserList().get(usahName).setNation(true, n);
						} catch (Exception e) {
							failure = true;
							getBaseCore().log("Error adding player to nation: " + e.getMessage());
						} finally {
							if (failure) {
								displayMessageToPlayer(playah, ChatColor.RED + "Sorry, there was a problem adding you to " + args[0] + ".");
							} else {
								displayMessageToPlayer(playah, ChatColor.GREEN + "Welcome to " + args[0] + ", " + usahName);
							}
						}
					} else {
						displayMessageToPlayer(playah, "That nation does not exist!");
					}
				} else {
					displayMessageToPlayer(playah, "You are already a citizen of a nation!");
				}
			}
			//chat command to join a guild
			if (cmd.getName().equalsIgnoreCase("JoinGuild") || cmd.getName().equalsIgnoreCase("jg")) {
				if (JamesBond.doesPlayerBelongToCountry(usahName)) {
					if (!JamesBond.doesPlayerBelongToGuild(usahName)) {
						if (JamesBond.getGuilds().containsKey(args[0])) {
							boolean failed = false;
							Guild g = JamesBond.getGuilds().get(args[0]);
							try {
								g.addMemberWithRank(usahName,g.getRanksInOrder().elementAt(0));
								getBaseCore().getUserList().get(usahName).setGuild(true, g);
							} catch (Exception e) {
								failed = true;
								e.printStackTrace();
							} finally {
								if (failed) {
									displayMessageToPlayer(playah, ChatColor.RED + "Sorry, there was a problem adding you to " + args[0] + ".");
								} else {
									displayMessageToPlayer(playah, ChatColor.GREEN + "Welcome to " + args[0] + ", " + usahName);
									JamesBond.getNationForPlayer(usahName).refreshCitizens();
								}
							}
						} else {
							displayMessageToPlayer(playah, "No guild with that name exists!");
						}
					} else {
						displayMessageToPlayer(playah, "You are already in a guild!");
					}
				} else {
					displayMessageToPlayer(playah, "You must belong to a nation to join a guild!");
				}
			}
			//command to get the nations/guilds/ranks (list)
			if (cmd.getName().equalsIgnoreCase("list")) {
				if (args[0].equalsIgnoreCase("nations") || args[0].equalsIgnoreCase("nation")) {
					displayMessageToPlayer(playah, "Nation list: " + getBaseCore().getNationNames().toString());
				} else if (args[0].equalsIgnoreCase("guilds") || args[0].equalsIgnoreCase("guild")) {
					if (JamesBond.doesPlayerBelongToCountry(usahName)) {
						Set<String> guildList = JamesBond.guildGetNames(JamesBond.getNationForPlayer(usahName).getGuilds());
						displayMessageToPlayer(playah, "Guild list: " + guildList.toString());
					} else {
						displayMessageToPlayer(playah, ChatColor.RED + "You must belong to a nation to get the list of guilds!");
					}
				} else if (args[0].equalsIgnoreCase("ranks") || args[0].equalsIgnoreCase("rank")) {
					if (JamesBond.doesPlayerBelongToGuild(usahName)) {
						Guild g = JamesBond.getGuildForPlayer(usahName);
						if (g == null) {
							getBaseCore().log("There was an error getting the ranks for a player " + usahName);
							displayMessageToPlayer(playah, "There was an error getting the ranks for this guild.");
							return true;
						}
						Vector<String> rankList = JamesBond.rankGetNames(g.getRanksInOrder());
						displayMessageToPlayer(playah, "Rank List: " + rankList.toString());
					} else {
						displayMessageToPlayer(playah, ChatColor.RED + "You must belong to a guild to get the list of ranks!");
					}
				}
			}
			// set guild spawn
			if (cmd.getName().equalsIgnoreCase("SetGuildSpawn") || cmd.getName().equalsIgnoreCase("sgs")) {
				Location currentLocation = playah.getLocation();
				if (JamesBond.getGuildNames().contains(args[0])) {
					try {
						JamesBond.getGuilds().get(args[0]).setSpawnPoint(currentLocation);
					} catch (Exception e) {
						e.printStackTrace();
						displayMessageToPlayer(playah, ChatColor.RED + "Sorry, there was an error.");
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Sends a given message to the given player.
	 * @param p {@link Player}
	 * @param m {@link String} Message
	 */
	private void displayMessageToPlayer(Player p, String m) {
		p.sendMessage(m);
	}
}