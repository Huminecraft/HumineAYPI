package com.aypi.utils.xml.balises;

import org.bukkit.entity.Player;
import org.w3c.dom.NamedNodeMap;

import com.aypi.utils.xml.MCBalise;

public class PlayerJoinBalise extends MCBalise {

	public final static String NAME = "on-player-join";
	
	public PlayerJoinBalise() {
		super(NAME);
	}

	@Override
	public void execute(Player player) {
		for (MCBalise mcb : getChildrens()) {
			mcb.execute(player);
		}
	}

	@Override
	public void setUpAttributes(NamedNodeMap namedNodeMap) {
		
	}

	
	
}