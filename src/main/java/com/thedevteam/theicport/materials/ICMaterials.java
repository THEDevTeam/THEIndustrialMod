package com.thedevteam.theicport.materials;

//Equivilent to Vanilla's VanillaMaterials - All Blocks should be registered here

import org.spout.api.material.Material;
import org.spout.vanilla.material.block.Ore;
import org.spout.vanilla.material.generic.GenericItem;

public final class ICMaterials extends Material {
	public ICMaterials(String name, int typeId) {
		super(name, typeId);
	}

	private static boolean initialized = false;
	// Blocks
	public static final Ore URANIUM_ORE = register(new Ore("Uranium Ore", 1));

	// Items
	public static final GenericItem URANIUM = register(new GenericItem(
			"Uranium", 2));

	public static void initialize() {
		if (initialized)
			return;
		URANIUM_ORE.setDrop(URANIUM);
		initialized = true;
	}
}
