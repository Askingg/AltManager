package me.askingg.altcontrol.main;

import org.bukkit.plugin.java.JavaPlugin;

import me.askingg.altcontrol.commands.AltsCMD;
import me.askingg.altcontrol.events.PlayerJoin;
import me.askingg.altcontrol.utils.Files;

public class Main extends JavaPlugin {

	public void onEnable() {
		Files.base();
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getCommand("alts").setExecutor(new AltsCMD());
	}
}
