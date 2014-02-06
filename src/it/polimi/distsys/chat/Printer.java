package it.polimi.distsys.chat;

import it.polimi.distsys.communication.Layer;

import java.lang.reflect.Field;

public class Printer {

	private static ChatFrame chatFrame = ChatFrame.get();

	public static void printDebug(Class<?> clazz, String string) {
		boolean debug = false;
		if (Layer.class.isAssignableFrom(clazz)) {
			try {
				Field fld = clazz.getField("DEBUG");
				fld.setAccessible(true);
				debug = fld.getBoolean(null);
			} catch (IllegalArgumentException | IllegalAccessException
					| NoSuchFieldException | SecurityException e) {
				System.out.println(clazz.getSimpleName() + ": no DEBUG field");
				debug = false;
			}
		} else {
			debug = Peer.DEBUG;
		}

		if (debug) {
			chatFrame.print(clazz.getSimpleName() + ": " + string);
			// System.err.println(clazz.getSimpleName() + ": " + strin);
		}
	}

	public static void print(String string) {
		chatFrame.print(string);
		// System.out.println(string);
	}

}
