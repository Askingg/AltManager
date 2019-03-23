package me.askingg.altcontrol.utils;

import org.bukkit.ChatColor;

public class Format {

	public static String prefix = Files.config.getString("Messages.Prefix");

	public static String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
}
