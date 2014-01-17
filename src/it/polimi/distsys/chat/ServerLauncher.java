package it.polimi.distsys.chat;

import it.polimi.distsys.Config;
import it.polimi.distsys.communication.components.Printer;

import java.io.IOException;


public class ServerLauncher {

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		
		Config.init();
		server.startDisplayer();
		
		Printer.printDebug(ServerLauncher.class, "Server ready");
	}

}
