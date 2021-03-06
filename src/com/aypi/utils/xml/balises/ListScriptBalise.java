package com.aypi.utils.xml.balises;

import org.bukkit.entity.Player;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.aypi.utils.xml.MCBalise;
import com.aypi.utils.xml.XMLFile;
import com.aypi.utils.xml.script.ListScript;
import com.aypi.utils.xml.script.Variable;

public class ListScriptBalise extends MCBalise {

	public static final String NAME = "list";
	
	private String name;
	
	public ListScriptBalise() {
		super(NAME);
		name = "";
	}

	@Override
	public void setUpCustomAttributes(NamedNodeMap namedNodeMap) {
		Node name = namedNodeMap.getNamedItem("name");
		if (name != null) {
			this.name = name.getNodeValue();
		}
	}

	@Override
	public void customExecute(Player player, XMLFile xmlFile) {
		
		ListScript list = new ListScript(name);
		if (!getPureContent().equalsIgnoreCase(""))
			list.addValues(getContent());
		
		xmlFile.getScriptManager().addListScript(list);
		
		xmlFile.getScriptManager().addVariable(new Variable("%"+name.toUpperCase()+"_LENGTH%", ""+list.getValues().size()));
		
	}

	@Override
	public MCBalise getInstance() {
		return new ListScriptBalise();
	}

}
