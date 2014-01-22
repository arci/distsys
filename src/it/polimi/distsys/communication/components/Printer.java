package it.polimi.distsys.communication.components;

import java.lang.reflect.Field;

public class Printer {

	public static void printDebug(Class<?> clazz, String string) {
		boolean debug = false;
		try {
			Field fld = clazz.getField("DEBUG");
			fld.setAccessible(true);
			debug = fld.getBoolean(null);
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			debug = true;
		}

		if (debug) {
			System.err.println(clazz.getSimpleName() + ": " + string);
		}
	}

	public static void print(String string) {
		System.out.println(string);
	}

}
