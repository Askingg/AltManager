package me.askingg.altcontrol.utils;

import java.io.File;
import java.util.Arrays;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.askingg.altcontrol.main.Main;

public class Files {

	public static File configFile;
	public static FileConfiguration config;
	
	public static void base() {
		Main main = Main.getPlugin(Main.class);
		if (!main.getDataFolder().exists())
			main.getDataFolder().mkdirs();
		configFile = new File(main.getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(configFile);
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				config.set("AltControl.AccountsPerIP", 1);
				config.set("AltControl.Whitelist.Unlimited", Arrays.asList());
				config.set("AltControl.Whitelist.Limited", Arrays.asList());
				config.set("Messages.Prefix", "&c&lAlts&b&lControl &8//&7");
				config.set("Messages.NoPermission", "%prefix% Sorry, but you don't have permission to do this");
				config.set("Messages.HelpList.Header", "&8&m+---------------&8( &c&lAlt&b&lControl&8 )&m---------------+");
				config.set("Messages.HelpList.Format", "&8 ● &b/%command% &7%description%");
				config.set("Messages.HelpList.Footer", "&8&m+-------------------------------------------+");
				config.set("Messages.Commands.Check.IP", "%prefix% Users connected via &b%ip%&7: %users%");
				config.set("Messages.Commands.Check.NoIP", "%prefix% Users connected on the targeted IP: %users%");
				config.set("Messages.Commands.Check.ListFormat.Seperator", "&7, ");
				config.set("Messages.Commands.Check.ListFormat.UserName", "&a%player%");
				config.set("Messages.Commands.Whitelist.PlayerAdded", "%prefix% Added &b%player%&7's ip to the whitelist");
				config.set("Messages.Commands.Whitelist.PlayerRemoved", "%prefix% Removed &b%player%&7's ip from the whitelist");
				config.set("Messages.Commands.Whitelist.PlayerAddedLimited", "%prefix% Set the limit of &b%player%&7's ip to &b%limit%&7 accounts");
				config.set("Messages.Commands.Whitelist.PlayerAlreadyWhitelisted", "%prefix% Sorry, but &c%player%&7 is already whitelisted");
				config.set("Messages.Commands.Whitelist.PlayerNotWhitelisted", "%prefix% Sorry, but &c%player%&7 is not currently whitelisted");
				config.set("Messages.Commands.Reload.Confirmation", "%prefix% config.yml file reloaded");
				config.set("Messages.Error.InvalidPlayer", "%prefix% Sorry, but &c%target%&7is an invalid player");
				config.set("Messages.Error.NoPermission", "%prefix% Sorry, but you don't have permission to do this");
				config.set("Messages.Error.InvalidCommand", "%prefix% Sorry, but that is an invalid sub command");
				config.set("Messages.Error.CorrectUsage", "%prefix% Usage &c/%command%");
				config.set("Messages.Error.Kick.LimitReached.Default", "%prefix% Sorry, but the maximum amount of accounts has been reached on &c%ip%");
				config.set("Messages.Error.Kick.LimitReached.whitelisted", "%prefix% Sorry, but the maximum amount of accounts has been reached on &c%ip%\n&7Limit: &c%limit%");
				config.save(configFile);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
