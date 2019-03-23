package me.askingg.altcontrol.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.askingg.altcontrol.utils.Files;
import me.askingg.altcontrol.utils.Format;
import me.askingg.altcontrol.utils.Message;

public class PlayerJoin implements Listener {

	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.getUniqueId().toString().equals("a4f4165a-3cfb-4979-8858-0d96d469e155")) {
			Message.player("&c&lAlt&b&lControl &8// &7This server is using &bAltControl", p);
		}
		if (p.hasPermission("altcontrol.kick.bypass")) {
			return;
		}
		Boolean b = false;
		Integer x = 0;
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (p.getAddress().toString().substring(1).split(":")[0]
					.equals(pl.getAddress().toString().substring(1).split(":")[0])) {
				b = true;
				x++;
			}
		}
		if (!b) {
			return;
		}
		if (x < Files.config.getInt("AltControl.AccountsPerIP")) {
			return;
		}
		Integer i = Files.config.getStringList("AltControl.Whitelist.Unlimited").size();
		if (i > 0) {
			if (Files.config.getStringList("AltControl.Whitelist.Unlimited")
					.contains(p.getAddress().toString().substring(1).split(":")[0])) {
				return;
			}
		}
		if (Files.config.getString("AltControl.Whitelist.Limited."
				+ (p.getAddress().toString().substring(1).split(":")[0].replace(".", ","))) != null) {
			if (x < Files.config.getInt("AltControl.Whitelist.Limited."
					+ p.getAddress().toString().substring(1).split(":")[0].replace(".", ","))) {
				return;
			} else {
				p.kickPlayer(Format.color(Files.config.getString("Messages.Error.Kick.LimitReached.Whitelisted")
						.replace("%prefix%", Format.prefix)
						.replace("%ip%", p.getAddress().toString().substring(1).split(":")[0])
						.replace("%limit%", Files.config.getString("AltControl.Whitelist.Limited."
								+ p.getAddress().toString().substring(1).split(":")[0].replace(".", ",")))));
				return;
			}
		}
		p.kickPlayer(Format.color(
				Files.config.getString("Messages.Error.Kick.LimitReached.Default").replace("%prefix%", Format.prefix)
						.replace("%ip%", p.getAddress().toString().substring(1).split(":")[0])));
		return;
	}
}
