package com.aypi;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import com.aypi.events.BaliseExecutor;
import com.aypi.manager.ErrorManager;
import com.aypi.manager.EventManager;
import com.aypi.manager.MenuManager;
import com.aypi.manager.ParticleManager;
import com.aypi.manager.TimerManager;
import com.aypi.manager.XMLFileManager;
import com.aypi.manager.ZoneManager;
import com.aypi.utils.Timer;
import com.aypi.utils.inter.TimerFinishListener;
import com.aypi.utils.xml.XMLFile;

public class Aypi extends JavaPlugin {

	final static String VERSION = "1.4.8";
	private static MenuManager menuManager;
	private static ZoneManager zoneManager;
	private static TimerManager timerManager;
	private static ParticleManager particleManager;
	private static XMLFileManager xmlFileManager;
	private static ErrorManager errorManager;

	/*
	 * Class principal de l'API AYPI
	 */

	public void onEnable() {
		System.out.println("Aypi V" + VERSION + " loaded");
		saveDefaultConfig();
		menuManager = new MenuManager();
		zoneManager = new ZoneManager();
		timerManager = new TimerManager();
		particleManager = new ParticleManager();
		xmlFileManager = new XMLFileManager();
		System.out.println("SML File Manager initialise");
		errorManager = new ErrorManager();
		new EventManager(this);
		
		if (this.getConfig().getBoolean("xmlloader")) {
			
			File file = new File("./plugins/Aypi/xml-reader/");
			file.mkdirs();
			System.out.println("[AYPI] "+file.listFiles().length+" file to load found !");
			for (File f : file.listFiles()) {
				System.out.println("	- "+f.getName());
				XMLFile xmlFile = new XMLFile(f);
				xmlFile.load();
				Aypi.getXMLFileManager().addXMLFile(xmlFile);
			}
			
		}
		new Timer(this, 3, new TimerFinishListener() {
			
			@Override
			public void execute() {
				System.out.println("---------------[SERVER START]---------------");
				BaliseExecutor.onServerStart();
			}
		}, true).start();
	}

	public void onDisable() {
		System.out.println("Aypi V" + VERSION + " unloaded");
		System.out.println("---------------[SERVER STOP]---------------");
		BaliseExecutor.onServerStop();
	}

	public static MenuManager getMenuManager() {
		return menuManager;
	}

	public static ZoneManager getZoneManager() {
		return zoneManager;
	}

	public static TimerManager getTimerManager() {
		return timerManager;
	}
	
	public static ParticleManager getParticleManager() {
		return particleManager;
	}
	
	public static XMLFileManager getXMLFileManager() {
		return xmlFileManager;
	}
	
	public static ErrorManager getErrorManager() {
		return errorManager;
	}
}
