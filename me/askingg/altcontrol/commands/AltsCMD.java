package me.askingg.altcontrol.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.askingg.altcontrol.utils.Files;
import me.askingg.altcontrol.utils.Message;

public class AltsCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			Message.sender(Files.config.getString("Messages.HelpList.Header"), sender);
			Message.sender(Files.config.getString("Messages.HelpList.Format").replace("%command%", "Alts")
					.replace("%description%", "View the help list"), sender);
			Message.sender(Files.config.getString("Messages.HelpList.Format").replace("%command%", "Alts Check")
					.replace("%description%", "Check who is connected on the same ip"), sender);
			Message.sender(Files.config.getString("Messages.HelpList.Format").replace("%command%", "Alts Whitelist")
					.replace("%description%", "Edit the user/ip whitelists"), sender);
			Message.sender(Files.config.getString("Messages.HelpList.Format").replace("%command%", "Alts Reload")
					.replace("%description%", "Apply changes in the config file"), sender);
			Message.sender(Files.config.getString("Messages.HelpList.Format").replace("%command%", "Alts Dev")
					.replace("%description%", "View the developer"), sender);
			Message.sender(Files.config.getString("Messages.HelpList.Footer"), sender);
		} else {
			if (args[0].equalsIgnoreCase("check")) { // Alts Check <Player>
				if (sender instanceof ConsoleCommandSender || sender.hasPermission("altcontrol.command.check")) {
					if (args.length == 2) {
						Player p = Bukkit.getPlayer(args[1]);
						if (p != null) {
							String m;
							if (sender instanceof ConsoleCommandSender
									|| sender.hasPermission("altcontrol.command.check.ip")) {
								m = Files.config.getString("Messages.Commands.Check.IP").replace("%ip%",
										p.getAddress().toString().substring(1).split(":")[0]);
							} else {
								m = Files.config.getString("Messages.Commands.Check.NoIP");
							}
							String l = "";
							for (Player pl : Bukkit.getOnlinePlayers()) {
								if (p.getAddress().toString().substring(1).split(":")[0]
										.equals(pl.getAddress().toString().substring(1).split(":")[0])) {
									if (l.equals("")) {
										l = Files.config.getString("Messages.Commands.Check.ListFormat.UserName")
												.replace("%player%", pl.getName());
									} else {
										l = l + Files.config.getString("Messages.Commands.Check.ListFormat.Seperator")
												+ Files.config.getString("Messages.Commands.Check.ListFormat.UserName")
														.replace("%player%", pl.getName());
									}
								}
							}
							m = m.replace("%users%", l);
							Message.sender(m, sender);
							return true;
						} else {
							Message.sender(
									Files.config.getString("Messages.Error.InvalidPlayer").replace("%target%", args[1]),
									sender);
							return true;
						}
					} else {
						Message.sender(Files.config.getString("Messages.Error.CorrectUsage").replace("%command%",
								"Alts Check <Player>"), sender);
						return true;
					}
				} else {
					Message.sender(Files.config.getString("Messages.Error.NoPermission"), sender);
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("whitelist")) { // Alts Whitelist <Add/Remove> <Player> (Limit)
				if (args.length == 3 || args.length == 4) {
					if (sender instanceof ConsoleCommandSender || sender.hasPermission("altcontrol.whitelist.manage")) {
						Player p = Bukkit.getPlayer(args[2]);
						if (p != null) {
							if (args.length == 3) { // Alts Whitelist <Add/Remove>
								List<String> l = Files.config.getStringList("AltControl.Whitelist.Unlimited");
								if (args[1].equalsIgnoreCase("add")) {
									if (l.contains(p.getAddress().toString().substring(1).split(":")[0])) {
										Message.sender(Files.config
												.getString("Messages.Commands.Whitelist.PlayerAlreadyWhitelisted")
												.replace("%player%", p.getName()), sender);
										return true;
									}
									l.add(p.getAddress().toString().substring(1).split(":")[0]);
									try {
										Files.config.set("AltControl.Whitelist.Unlimited", l);
										Files.config.save(Files.configFile);
										Message.sender(Files.config.getString("Messages.Commands.Whitelist.PlayerAdded")
												.replace("%player%", p.getName()), sender);
										return true;
									} catch (Exception ex) {
										ex.printStackTrace();
										return true;
									}

								}
								if (args[1].equalsIgnoreCase("remove")) {
									if (l.contains(p.getAddress().toString().substring(1).split(":")[0])) {
										l.remove(p.getAddress().toString().substring(1).split(":")[0]);
										try {
											Files.config.set("AltControl.Whitelist.Unlimited", l);
											Files.config.save(Files.configFile);
											Message.sender(
													Files.config.getString("Messages.Commands.Whitelist.PlayerRemoved")
															.replace("%player%", p.getName()),
													sender);
											return true;
										} catch (Exception ex) {
											ex.printStackTrace();
											return true;
										}
									} else {
										Message.sender(Files.config
												.getString("Messages.Commands.Whitelist.PlayerNotWhitelisted")
												.replace("%player%", p.getName()), sender);
										return true;
									}
								}
							}
							if (args.length == 4) { // Alts Whitelist <Add/Remove> <Player> <Limit>
								if (args[1].equalsIgnoreCase("add")) {
									if (Files.config.getConfigurationSection("AltControl.Whitelist.Limited") != null) {
									}
									try {
										Integer.parseInt(args[3]);
									} catch (Exception ex) {
										Message.sender(Files.config.getString("Messagse.Error.InvalidInteger"), sender);
										return true;
									}
									Files.config.set("AltControl.Whitelist.Limited."
											+ (p.getAddress().toString().substring(1).split(":")[0].replace(".", ",")),
											Integer.valueOf(args[3]));
									try {
										Files.config.save(Files.configFile);
										Message.sender(
												Files.config.getString("Messages.Commands.Whitelist.PlayerAddedLimited")
														.replace("%player%", p.getName()).replace("%limit%", args[3]),
												sender);
										return true;
									} catch (Exception ex) {
										ex.printStackTrace();
										return true;
									}
								}
								if (args[1].equalsIgnoreCase("remove")) {
									if (Files.config.getString("AltControl.Whitelist.Limited."
											+ (p.getAddress().toString().substring(1).split(":")[0].replace(".",
													","))) != null) {
										Files.config.set("AltControl.Whitelist.Limited."
												+ (p.getAddress().toString().substring(1).split(":")[0].replace(".",
														",")),
												null);
										try {
											Files.config.save(Files.configFile);
											Message.sender(Files.config
													.getString("Messages.Commands.Whitelist.PlayerRemoved")
													.replace("%player%", p.getName()),
													sender);
											return true;
										} catch (Exception ex) {
											ex.printStackTrace();
											return true;
										}
									} else {
										Message.sender(Files.config
												.getString("Messages.Commands.Whitelist.PlayerNotWhitelisted")
												.replace("%player%", p.getName()), sender);
										return true;
									}
								}
							}
						} else {
							Message.sender(
									Files.config.getString("Messages.Error.InvalidPlayer").replace("%target%", args[1]),
									sender);
							return true;
						}
					} else {
						Message.sender(Files.config.getString("Messages.Error.NoPermission"), sender);
						return true;
					}
				} else {
					Message.sender(Files.config.getString("Messages.Error.CorrectUsage").replace("%command%",
							"Alts Whitelist <Add/Remove> <Player> <AccountLimit>"), sender);
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("reload")) {
				if (sender instanceof ConsoleCommandSender || sender.hasPermission("altcontrol.command.reload")) {
					Files.base();
					Message.sender(Files.config.getString("Messages.Commands.Reload.Confirmation"), sender);
					return true;
				} else {
					Message.sender(Files.config.getString("Messages.Error.NoPermission"), sender);
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("dev")) {
				Message.sender("%prefix% &7Developer Information:", sender);
				Message.sender("&8 ● &7Developer: &b&lAskingg", sender);
				Message.sender("&8 ● &7Spigot Page: &bbit.ly/AskinggSpigot", sender);
				Message.sender("&8 ● &7YouTube Channel: &bbit.ly/AskinggYouTube", sender);
				return true;
			}
			Message.sender(Files.config.getString("Messages.Error.InvalidCommand"), sender);
			return true;
		}
		return false;
	}

}
