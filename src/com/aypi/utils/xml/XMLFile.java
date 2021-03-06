package com.aypi.utils.xml;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.entity.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.aypi.Aypi;
import com.aypi.utils.xml.balises.ListScriptAddBalise;
import com.aypi.utils.xml.balises.ListScriptBalise;
import com.aypi.utils.xml.balises.ListScriptRemoveBalise;
import com.aypi.utils.xml.balises.VariableBalise;
import com.aypi.utils.xml.script.ScriptManager;

public class XMLFile {
	
	private File file;
	private ArrayList<MCBalise> mcBalises;

	private ScriptManager scriptManager;
	
	public XMLFile(File file) {
		this.file = file;
		this.mcBalises = new ArrayList<MCBalise>();
		this.scriptManager = new ScriptManager();
	}
	
	public void load() {

		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(file);
			
			//Affichage du prologue
		    System.out.println("*************PROLOGUE************");
		    System.out.println("name : "+file.getName());
		    System.out.println("version : " + document.getXmlVersion());
		    System.out.println("encode : " + document.getXmlEncoding());		
	        System.out.println("standalone : " + document.getXmlStandalone());
		    System.out.println("*************ELEMENTS************");
	        
	        final Element racine = document.getDocumentElement();
	        
	        if (racine.getNodeName().equalsIgnoreCase("server")) {
	        	
	        	final NodeList racineNoeuds = racine.getChildNodes();
	        	for (MCBalise m : createBalise(racineNoeuds)) {
		        	this.mcBalises.add(m);
		        	if (m instanceof VariableBalise || m instanceof ListScriptBalise || m instanceof ListScriptAddBalise || m instanceof ListScriptRemoveBalise) {
		        		m.execute(null, this);
		        	}
	        	}	        	
	        }
	        else
	        {
	        	System.out.println("ERROR: your balise root should be 'server'.");
	        }
			
		} catch(Exception e) {e.printStackTrace();}
		
		printBalise(mcBalises);
		
	}
	
//	public void addNewBalise(String[] root, String[] att, String value) {
//		FileManager fileManager = new FileManager(file);
//		
//		int compteur
//		
//		for (String line : fileManager.getTextFile()) {
//			if (line.contains(root[0])) {
//				
//			}
//		}
//		
//	}
	
//	private void shift(String[] args) {
//		String[] argsShifted = new String[args.length - 1];
//		
//		for (int i = 1 ; i < args.length ; i++) {
//			argsShifted[i-1] = args[i];
//		}
//		
//		args = argsShifted;
//	}
	
	private void printBalise(ArrayList<MCBalise> mcb) {
		for (MCBalise m : mcb) {
			if (m.haveChildren()) {
				System.out.println("\\/"+m.getName());
				printBalise(m.getChildrens());
				System.out.println("/\\"+m.getName());
			} else {
				System.out.println("- "+m.getName()+" -");
			}
			
		}
	}
	
	private ArrayList<MCBalise> createBalise(NodeList node) {
		
		ArrayList<MCBalise> mcBalises = new ArrayList<MCBalise>();

		for (int i = 0 ; i < node.getLength() ; i++) {
    		
			if (node.item(i).getNodeType() == Node.ELEMENT_NODE) {
				for (MCBalise mc : Aypi.getXMLFileManager().getMCBalises()) {
					if (node.item(i).getNodeName().equalsIgnoreCase(mc.getName())) {				
						MCBalise mb = mc.getInstance();
						mb.setUpAttributes(node.item(i).getAttributes());
						mb.setContent(node.item(i).getTextContent(), this, 0);
						if (node.item(i).hasChildNodes()) {
							for (MCBalise children : createBalise(node.item(i).getChildNodes())) {
								mb.addChildren(children);
							}
						}
						mcBalises.add(mb);
					}
				}
				
				
			}
    		
    	}
		
		return mcBalises;

	}
	
	public void destroy() {
		while (mcBalises.size() != 0) {
			mcBalises.remove(0);
		}
		Aypi.getXMLFileManager().removeXMLFile(this);
	}
	
	public void addMCBalise(MCBalise mcBalise) {
		mcBalises.add(mcBalise);
	}
	
	public ArrayList<MCBalise> getMCBalises() {
		return mcBalises;
	}
	
	public File getFile() {
		return file;
	}
	
	public ScriptManager getScriptManager() {
		return this.scriptManager;
	}

	public void executesAllBalises(String name, Player player) {
		
		for (MCBalise mcb : mcBalises) {
			if (mcb.getName().equalsIgnoreCase(name)) {
				mcb.execute(player, this);
			}
			
		}
		
	}
	
}
