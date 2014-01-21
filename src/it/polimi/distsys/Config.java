package it.polimi.distsys;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class Config {
	private static Properties actions;
	private static Properties layers;
	private static String ACTIONS_FILE_PATH = "actions.properties";
	private static String LAYERS_FILE_PATH = "layers.properties";
	
	public static void init(){
		actions = new Properties();
		layers = new Properties();
		
		try {
			actions.load(new FileInputStream(ACTIONS_FILE_PATH));
			layers.load(new FileInputStream(LAYERS_FILE_PATH));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getAction(String key) throws ClassNotFoundException{
		String value = actions.getProperty(key);
		if(value == null){
			throw new ClassNotFoundException();
		}
		return value;
	}
	
	public static Set<String> getActions() throws ClassNotFoundException{
		return actions.stringPropertyNames();
	}
	
	public static String getLayer(String key) throws ClassNotFoundException{
		String value = layers.getProperty(key);
		if(value == null){
			throw new ClassNotFoundException();
		}
		return value;
	}
	
	public static Set<String> getLayers() throws ClassNotFoundException{
		return layers.stringPropertyNames();
	}

}
