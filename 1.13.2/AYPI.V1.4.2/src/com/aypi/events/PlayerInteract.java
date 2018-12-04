package com.aypi.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.aypi.Aypi;
import com.aypi.utils.Zone;

public class PlayerInteract implements Listener {
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();

		Location loc = player.getLocation();

		for (Zone zone : Aypi.getZoneManager().getZones()) {
			if (zone.containLocation(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()))) {
				zone.getZoneListener().onPlayerTryToInteractEvent(player, e);
			}
		}
	}

}