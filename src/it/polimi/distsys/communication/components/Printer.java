package it.polimi.distsys.communication.components;

import java.lang.reflect.Field;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.layers.Layer;

public class Printer {

	public static void printDebug(Class<?> clazz, String string) {
		boolean debug = false;
		if(Layer.class.isAssignableFrom(clazz)){
			try {
				Field fld = clazz.getField("DEBUG");
				fld.setAccessible(true);
				debug = fld.getBoolean(null);
			} catch (IllegalArgumentException | IllegalAccessException
					| NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}else{
			debug = Peer.DEBUG;
		}
		
		if (debug) {
			System.err.println(clazz.getSimpleName() + ": " + string);
		}
	}

	public static void print(String string) {
		System.out.println(string);
	}

}
