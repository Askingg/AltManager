package me.askingg.altcontrol.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message {
	
	public static void console(String msg) {
		Bukkit.getConsoleSender().sendMessage(msg.replace("%prefix%", Format.prefix));
	}

	public static void player(String msg, Player player) {
		player.sendMessage(Format.color(msg.replace("%prefix%", Format.prefix)));
	}

	public static void sender(String msg, CommandSender sender) {
		sender.sendMessage(Format.color(msg.replace("%prefix%", Format.prefix)));
	}
}
