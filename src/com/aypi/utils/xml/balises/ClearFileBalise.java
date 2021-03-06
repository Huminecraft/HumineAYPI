package com.aypi.utils.xml.balises;

import org.bukkit.entity.Player;
import org.w3c.dom.NamedNodeMap;

import com.aypi.utils.xml.MCBalise;
import com.aypi.utils.xml.XMLFile;
import com.aypi.utils.xml.script.ListScript;
import com.aypi.utils.xml.script.Variable;

public class ClearFileBalise extends FileManagerBaliseAdaptor {

	public static final String NAME = "clear-file";
	
	public ClearFileBalise() {
		super(NAME);
		
	}

	@Override
	public void setUpCustomAttributes(NamedNodeMap namedNodeMap) {}

	@Override
	public void customExecute(Player player, XMLFile xmlFile) {
		
		getFileManager().clearFile();
		
		if (!getPureContent().equals(""))
			System.out.println(getString(getContent(), xmlFile));
		
		ListScript ls = new ListScript("%LINES_LIST%");
		for (String str : getFileManager().getTextFile()) {
			ls.addValues("'"+str+"'");
		}
		xmlFile.getScriptManager().addListScript(ls);
		xmlFile.getScriptManager().addVariable(new Variable("%LINES_LIST_LENGTH%", ""+ls.getValues().size()));
		
	}

	@Override
	public MCBalise getInstance() {
		return new ClearFileBalise();
	}

}
