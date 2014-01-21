package it.polimi.distsys.chat.actions;

import it.polimi.distsys.Config;
import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.components.Printer;

import java.lang.reflect.Field;
import java.util.Set;

public class DebugAction implements Action {

	@Override
	public void execute(Peer peer, String... param) {
		Class<?> clazz = Peer.class;
		boolean value = false;
		
		try {
			Set<String> layers = Config.getLayers();
			for(String layer : layers){
				if(param[0].equals(layer)){
					clazz = Class.forName(Config.getLayer(layer));
					value = Boolean.parseBoolean(param[1]);
				}
			}
			
			Field debug = clazz.getField("DEBUG");
			debug.setAccessible(true);
			debug.setBoolean(null, value);
			
			Printer.print(clazz.getSimpleName() + ": DEBUG set to " + value);
		} catch (ClassNotFoundException | IndexOutOfBoundsException e) {
			Printer.print(">>> debug-usage: /debug [<layer>] (true|false)");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

}
