package com.thedevteam.theicport;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

import com.thedevteam.theicport.utils.Downloader;

public class PartRegister {

	
	private final THEIndustrialMod tim;
	  private final File dir;
	  //private Map<String, File> PartJars;
	 // private final ClassLoader classLoader;
	  private Map<String, Part> Parts;

	  public PartRegister(THEIndustrialMod theindustrialmod)
	  {
	    tim = theindustrialmod;
	   // PartJars = new HashMap<String, File>();
	    Parts = new HashMap<String, Part>();
	    dir = new File(tim.getDataFolder(), "Parts");
	    dir.mkdirs();
	    //List<URL> urls = new ArrayList<URL>();
	    
	    
	    for (String file : dir.list()) {
	      if (!file.contains(".jar")) continue;
	      File f = new File(dir, file);
	      String partName = file.replace(".jar", "");
//	      if (PartJars.containsKey(partName)) {
//	        tim.log(Level.WARNING, "Part duplicated. Please remove: " + partName);
//	      } else {
//	    	  
//	    	  
//	    	  
//	    	  PartJars.put(partName, f);
//	    	  try {
//		          urls.add(f.toURI().toURL());
//		        } catch (MalformedURLException e) {
//		          tim.log(Level.SEVERE, e);
//		        }
//	      }
	      if(isLoaded(partName))continue;
	     Part p = loadPart(f);
	     if(p !=null){
	    	 if(resolveDependencies(p)){
	    		 addPart(p);
	    	 }
	     }
	      
	    }
	    
//	    ClassLoader c = tim.getClass().getClassLoader();
//	    classLoader = URLClassLoader.newInstance((URL[])urls.toArray(new URL[urls.size()]), c);
//	    loadParts();
	    tim.log(Level.INFO, "All Parts loaded. Active Parts: " + Parts.keySet());
	  }

	  public Map<String, Part> getActiveParts()
	  {
	    return Parts;
	  }

	  public Part getKioPack(String s) {
	    for (Part p : getActiveParts().values()) {
	      if (s.equalsIgnoreCase(p.getName())) return p;
	    }
	    return null;
	  }

//	  private void loadParts() {
//	    for (Entry<String,File> entry : PartJars.entrySet()) {
//	      if (isLoaded((String)entry.getKey())) {
//	        continue;
//	      }
//	      Part p = loadPart((File)entry.getValue());
//	      if (p != null) {
//	        addPart(p);
//	      }
//	    }
//	  }

	  private boolean resolveDependencies(Part p) {
		List<String> deps = p.getDependencies();
		for (String dep:deps){
			// check to see if already loaded
			if(isLoaded(dep))continue;
			//check if part is in directory
			File f = null;
			for (String file : dir.list()) {
			      if (!file.contains(".jar")) continue;
			      String partName = file.replace(".jar", "");
			      if (!partName.equalsIgnoreCase(dep))continue;
			      f = new File(dir, file);
			      }
			if(f == null){
				f =Downloader.fetch("", dep+".jar"); //TODO: Create Repo
			}
			Part d = loadPart(f);
			if(d !=null){
		    	 if(resolveDependencies(d)){
		    		 addPart(d);
		    	 }else{
		    		 return false;
		    	 }
		     }else{
		    	 return false;
		     }
		}
		return true;
		
	}

	private void addPart(Part p) {

	    Parts.put(p.getName(), p);
	    tim.log(Level.INFO, "Part " + p.getName() + " Loaded");
	  }

	  private Part loadPart(File f) {
	    try {
	      JarFile jarFile = new JarFile(f);
	      Enumeration<JarEntry> entries = jarFile.entries();

	      String mainClass = null;
	      while (entries.hasMoreElements()) {
	        JarEntry element = (JarEntry)entries.nextElement();
	        if (element.getName().equalsIgnoreCase("Pack.yml")) {
	          BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(element)));
	          mainClass = reader.readLine().substring(6);
	          break;
	        }
	      }
	      
	      List<URL> url = new ArrayList<URL>();
	      
	      try {
	          url.add(f.toURI().toURL());
	        } catch (MalformedURLException e) {
	          tim.log(Level.SEVERE, e);
	        }
	      
	      ClassLoader c = tim.getClass().getClassLoader();
		    ClassLoader classLoader = URLClassLoader.newInstance((URL[])url.toArray(new URL[url.size()]), c);
	      if (mainClass != null){ 
	        Class<?> clazz = Class.forName(mainClass, true, classLoader);
	        Class<? extends Part> partClass = clazz.asSubclass(Part.class);
	        Constructor<? extends Part> ctor = partClass.getConstructor(new Class[] { tim.getClass() });
	        Part p = (Part)ctor.newInstance(new Object[] { tim });

	        return p;
	      }
	      throw new Exception();
	    } catch (Exception e) {
	      e.printStackTrace();
	      tim.log(Level.INFO, "Part " + f.getName() + " has failed to load.");
	    }
	    return null;
	  }

	  private boolean isLoaded(String key) {
	    return Parts.containsKey(key.toLowerCase());
	  }

	  public void enableAllPacks() {
	    for (Part p : Parts.values())
	      p.init();
	  }
}
