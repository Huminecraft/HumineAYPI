package com.aypi.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.aypi.Aypi;
import com.aypi.utils.Zone;
import com.aypi.utils.ZonePriorityBuffer;

public class PlayerPlaceBlock implements Listener {
	
	@EventHandler
	public void playerPlaceBlock(BlockPlaceEvent e) {
		
		Player player = e.getPlayer();
		Location loc = player.getLocation();
		ZonePriorityBuffer zpb = new ZonePriorityBuffer();
			
		for (Zone zone : Aypi.getZoneManager().getZones()) {
			if(zone.containLocation(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()))) {
				zpb.addZone(zone);
			}
		}
		
		for (Zone zone : zpb.getPriorityZones()) {
			zone.getZoneListener().onPlayerTryToPlaceBlock(player, e.getBlock(), e);
		}
		
		
	}
	
	

}
