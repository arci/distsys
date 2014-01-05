package it.polimi.distsys.components;

import it.polimi.distsys.chat.Peer;

public class Printer {

	public static void printDebug(String string) {
		if (Peer.DEBUG) {
			System.err.println(string);
		}
	}
	
	public static void print(String string) {
		System.out.println(string);
	}

}
