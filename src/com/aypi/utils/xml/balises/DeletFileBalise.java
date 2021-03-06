package com.aypi.utils.xml.balises;

import org.bukkit.entity.Player;
import org.w3c.dom.NamedNodeMap;

import com.aypi.utils.xml.MCBalise;
import com.aypi.utils.xml.XMLFile;

public class DeletFileBalise extends FileManagerBaliseAdaptor {

	public static final String NAME = "delet-file";
	
	public DeletFileBalise() {
		super(NAME);
	}

	@Override
	public void setUpCustomAttributes(NamedNodeMap namedNodeMap) {}

	@Override
	public void customExecute(Player player, XMLFile xmlFile) {
		getFileManager().getFile().delete();
		
		if (!getPureContent().equals(""))
			System.out.println(getString(getContent(), xmlFile));
		
		xmlFile.getScriptManager().removeListScript(xmlFile.getScriptManager().getListScript("%LINES_LIST%"));
		xmlFile.getScriptManager().removeVariable(xmlFile.getScriptManager().getVariable("%LINES_LIST_LENGTH%"));
	}

	@Override
	public MCBalise getInstance() {
		return new DeletFileBalise();
	}

}
