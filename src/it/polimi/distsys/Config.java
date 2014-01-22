package it.polimi.distsys;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.layers.causal.CausalLayer;
import it.polimi.distsys.communication.layers.multicast.MulticastLayer;
import it.polimi.distsys.communication.layers.reliable.ReliableLayer;
import it.polimi.distsys.communication.layers.secure.SecureLayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class Config {
	private static Properties actions;
	private static Properties layers;
	private static Properties conf;
	private static String ACTIONS_FILE_PATH = "actions.properties";
	private static String LAYERS_FILE_PATH = "layers.properties";
	private static String CONF_FILE_PATH = "conf.properties";
	
	public static void init(){
		actions = new Properties();
		layers = new Properties();
		conf = new Properties();
		
		try {
			actions.load(new FileInputStream(ACTIONS_FILE_PATH));
			layers.load(new FileInputStream(LAYERS_FILE_PATH));
			conf.load(new FileInputStream(CONF_FILE_PATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Peer.DEBUG = Boolean.parseBoolean(conf.getProperty("OTHER_DEBUG"));
		SecureLayer.DEBUG = Boolean.parseBoolean(conf.getProperty("SEC_DEBUG"));
		CausalLayer.DEBUG = Boolean.parseBoolean(conf.getProperty("CAUS_DEBUG"));
		ReliableLayer.DEBUG = Boolean.parseBoolean(conf.getProperty("REL_DEBUG"));
		MulticastLayer.DEBUG = Boolean.parseBoolean(conf.getProperty("MULTI_DEBUG"));
		MulticastLayer.ADDRESS = conf.getProperty("IP");
		MulticastLayer.PORT = Integer.parseInt(conf.getProperty("PORT"));
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
