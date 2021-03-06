package com.aypi.manager;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import com.aypi.utils.particle.ParticleForm;

public class ParticleManager {
	
	private ArrayList<ParticleForm> pfs;
	
	public ParticleManager() {
		pfs = new ArrayList<ParticleForm>();
		loop();
	}
	
	private void loop() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Aypi"), new Runnable() {

			@Override
			public void run() {
				for (int i = 0 ; i < pfs.size() ; i++) {
					ParticleForm pf = pfs.get(i);
					if (pf.isPlayed()) {
						pf.update();
					}
				}
			}
			
		}, 1, 1);
	}
	
	public void addParticleForm(ParticleForm pf) {
		pfs.add(pf);
	}
	
	public void removeParticleForm(ParticleForm pf) {
		pfs.remove(pf);
	}
	
	public ArrayList<ParticleForm> getParticleForms() {
		return pfs;
	}

}
