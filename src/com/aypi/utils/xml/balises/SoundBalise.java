package com.aypi.utils.xml.balises;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.aypi.utils.xml.MCBalise;
import com.aypi.utils.xml.XMLFile;

public class SoundBalise extends LocationBaliseAdaptor {

	public static final String NAME = "sound";
	
	private Location location;
	private boolean broadcast;
	
	
	public SoundBalise() {
		super(NAME);
		this.location = null;
		broadcast = false;
	}

	@Override
	public void setUpCustomAttributes(NamedNodeMap attributes) {
		
		Node node = attributes.getNamedItem("broadcast");
		
		String value = "";
		if (node != null) 
			value = attributes.getNamedItem("broadcast").getNodeValue();
		
		if (!value.equalsIgnoreCase("")) {
			broadcast = Boolean.getBoolean(value);
		}
	}

	@Override
	public void customExecute(Player player, XMLFile xmlFile) {
		
		if (player == null && !broadcast) {
			
			System.out.println("[AYPI] ERROR: Il n'y a pas de joueur a selectionner pour la balise sound...");
			
			return;
		}
			
		if (broadcast) {
			for (Player pls : Bukkit.getOnlinePlayers()) {
				if (this.location == null) {
					pls.playSound(pls.getLocation(), Sound.valueOf(getContent()), 5, 1);
				} else {
					pls.playSound(location, Sound.valueOf(getContent()), 5, 1);
				}
			}
		} else {
			if (this.location == null) {
				player.playSound(player.getLocation(), Sound.valueOf(getContent()), 5, 1);
			} else {
				player.playSound(location, Sound.valueOf(getContent()), 5, 1);
			}
		}
			
		
	}

	@Override
	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public Location getLocation() {
		return this.location;
	}
	
	@Override
	public MCBalise getInstance() {
		return new SoundBalise();
	}

}
