package com.aypi.utils.xml.balises;

import java.io.File;

import org.bukkit.entity.Player;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.aypi.manager.FileManager;
import com.aypi.utils.xml.MCBalise;
import com.aypi.utils.xml.XMLFile;
import com.aypi.utils.xml.script.ListScript;
import com.aypi.utils.xml.script.Variable;

public class FileManagerBalise extends MCBalise {

	public static final String NAME = "file-manager";
	
	private FileManager fileManager;
	private String path;
	
	public FileManagerBalise() {
		super(NAME);
		fileManager = null;
	}

	@Override
	public void setUpCustomAttributes(NamedNodeMap namedNodeMap) {
		Node path = namedNodeMap.getNamedItem("path");
		String pathS = "";
		if (path != null) {
			pathS = path.getNodeValue();
		}
		
		this.path = pathS;
	}

	@Override
	public void customExecute(Player player, XMLFile xmlFile) {
		
		path = getString(xmlFile.getScriptManager().compile(path, 0), xmlFile);
		fileManager = new FileManager(new File(path));
		
		for (MCBalise children : getChildrens()) {
			if (children instanceof FileManagerBaliseAdaptor) {
				((FileManagerBaliseAdaptor) children).setFileManager(fileManager);
			}
			children.execute(player, xmlFile);
		}
		ListScript ls = new ListScript("%LINES_LIST%");
		for (String str : fileManager.getTextFile()) {
			if (xmlFile.getScriptManager().isBoolean(str) || xmlFile.getScriptManager().isNumber(str)) {
				ls.addValues(str);
			} else {
				ls.addValues("'"+str+"'");
			}
		}
		xmlFile.getScriptManager().addListScript(ls);
		xmlFile.getScriptManager().addVariable(new Variable("%LINES_LIST_LENGTH%", ""+ls.getValues().size()));
		
		//TODO AJOURTER LE SYSTEM DE VARIABLE POUR TOUT LES FileManagerBaliseAdaptor
	}

	@Override
	public MCBalise getInstance() {
		return new FileManagerBalise();
	}
	
	public FileManager getFileManager() {
		return fileManager;
	}

}
