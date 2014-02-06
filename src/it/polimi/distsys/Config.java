package it.polimi.distsys;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.causal.CausalLayer;
import it.polimi.distsys.communication.multicast.MulticastLayer;
import it.polimi.distsys.communication.reliable.ReliableLayer;
import it.polimi.distsys.communication.secure.ClientSecureLayer;
import it.polimi.distsys.communication.secure.ServerSecureLayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;
import java.util.Set;

public class Config {
	private static Properties actions;
	private static Properties layers;
	private static Properties conf;
	private static String ACTIONS_FILE_PATH = "actions.properties";
	private static String LAYERS_FILE_PATH = "layers.properties";
	private static String CONF_FILE_PATH = "conf.properties";

	public static void init() {
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
		ClientSecureLayer.DEBUG = Boolean.parseBoolean(conf
				.getProperty("CSEC_DEBUG"));
		ServerSecureLayer.DEBUG = Boolean.parseBoolean(conf
				.getProperty("SSEC_DEBUG"));
		CausalLayer.DEBUG = Boolean
				.parseBoolean(conf.getProperty("CAUS_DEBUG"));
		ReliableLayer.DEBUG = Boolean.parseBoolean(conf
				.getProperty("REL_DEBUG"));
		MulticastLayer.DEBUG = Boolean.parseBoolean(conf
				.getProperty("MULTI_DEBUG"));

		try {
			Field address = MulticastLayer.class.getField("ADDRESS");
			Field port = MulticastLayer.class.getField("PORT");
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(address, address.getModifiers()
					& ~Modifier.FINAL);
			modifiersField.setInt(port, port.getModifiers()
					& ~Modifier.FINAL);
			
			address.set(null, conf.getProperty("IP")); 
			port.set(null, Integer.parseInt(conf.getProperty("PORT")));
			
			//let's undo...
			
			modifiersField.setInt(address, address.getModifiers()
					& Modifier.FINAL);
			modifiersField.setInt(port, port.getModifiers()
					& Modifier.FINAL);
			modifiersField.setAccessible(false);
		} catch (Exception e) {
			//something went bad
			Printer.print(e.getMessage());
		}
	}

	public static String getAction(String key) throws ClassNotFoundException {
		String value = actions.getProperty(key);
		if (value == null) {
			throw new ClassNotFoundException();
		}
		return value;
	}

	public static Set<String> getActions() throws ClassNotFoundException {
		return actions.stringPropertyNames();
	}

	public static String getLayer(String key) throws ClassNotFoundException {
		String value = layers.getProperty(key);
		if (value == null) {
			throw new ClassNotFoundException();
		}
		return value;
	}

	public static Set<String> getLayers() throws ClassNotFoundException {
		return layers.stringPropertyNames();
	}

}
