package it.polimi.distsys;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private static Properties actions;
	private static String ACTIONS_FILE_PATH = "src/it/polimi/distsys/actions.properties";
	
	public static void init(){
		actions = new Properties();
		
		try {
			actions.load(new FileInputStream(ACTIONS_FILE_PATH));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getAction(String key){
		return actions.getProperty(key);
	}

}
