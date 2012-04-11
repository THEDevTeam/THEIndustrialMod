package com.thedevteam.theindustrialmod;

import java.util.logging.Level;

import org.spout.api.plugin.CommonPlugin;

import com.thedevteam.theindustrialmod.materials.ICMaterials;

public class THEIndustrialMod extends CommonPlugin{

	@Override
	public void onDisable() {
            // TODO Auto-generated method stub
	}

	@Override
	public void onEnable() {
		ICMaterials.initialize();
		getLogger().log(Level.INFO, "THEIndustrialMod has been enabled");
	}

	public void log(Level warning, Object string) {
		// TODO Auto-generated method stub
	}

}
