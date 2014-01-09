package it.polimi.distsys.chat;

import it.polimi.distsys.Config;
import it.polimi.distsys.components.Printer;

import java.io.IOException;


public class ClientLauncher {
	public static void main(String[] args) throws IOException {
		Client client = new Client();
		
		Config.init();
		client.startDisplayer();
		client.startReader();
		
		Printer.printDebug(null, "Client ready");
	}

}
