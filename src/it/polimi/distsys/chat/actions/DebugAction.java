package it.polimi.distsys.chat.actions;

import it.polimi.distsys.Config;
import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.chat.Printer;

import java.lang.reflect.Field;
import java.util.Set;

public class DebugAction implements Action {

	@Override
	public void execute(Peer peer, String... param) {
		boolean value = false;

		try {
			if (param.length == 2) {
				Set<String> layers = Config.getLayers();
				value = Boolean.parseBoolean(param[1]);

				if (param[0].equals("all")) {
					for (String layer : layers) {
						Class<?> clazz = Class.forName(Config.getLayer(layer));
						Field debug = clazz.getField("DEBUG");
						debug.setAccessible(true);
						debug.setBoolean(null, value);
						Printer.print(">>> " + clazz.getSimpleName() + ": DEBUG set to "
								+ value);
					}
				} else {
					for (String layer : layers) {
						if (param[0].equals(layer)) {
							Class<?> clazz = Class.forName(Config
									.getLayer(layer));
							Field debug = clazz.getField("DEBUG");
							debug.setAccessible(true);
							debug.setBoolean(null, value);
							Printer.print(">>> " + clazz.getSimpleName()
									+ ": DEBUG set to " + value);
						}
					}
				}

			} else if (param.length == 1) {
				Peer.DEBUG = Boolean.parseBoolean(param[0]);
				Printer.print(">>> " + Peer.class.getSimpleName() + ": DEBUG set to "
						+ value);
			}
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
