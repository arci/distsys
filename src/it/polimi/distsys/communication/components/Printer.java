package it.polimi.distsys.communication.components;

import it.polimi.distsys.chat.Peer;

public class Printer {

	public static void printDebug(Class<?> clazz, String string) {
		if (Peer.DEBUG) {
			System.err.println(clazz.getSimpleName() + ": " + string);
		}
	}

	public static void print(String string) {
		System.out.println(string);
	}

}
