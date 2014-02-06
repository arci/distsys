package it.polimi.distsys.chat;

import it.polimi.distsys.Config;
import it.polimi.distsys.Printer;

import java.io.IOException;


public class ClientLauncher {
	public static void main(String[] args) throws IOException {
		Config.init();
		Printer.print("Welcome to SGC! Now type your messages...");
		Client client = new Client();
		client.startDisplayer();
		client.startReader();
		
		Printer.printDebug(ClientLauncher.class, "Client ready");
	}

}
