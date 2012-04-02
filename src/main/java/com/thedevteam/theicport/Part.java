package com.thedevteam.theicport;

import java.util.List;

public abstract class Part {

	private String name;
	private THEIndustrialMod tim;
	private List<String> dependencies;

	public String getName() {
		return name;
	}

	public abstract void init() ;
	
	public List<String> getDependencies(){
		return dependencies;
	}

}
