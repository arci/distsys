package it.polimi.distsys.chat;

import it.polimi.distsys.Config;
import it.polimi.distsys.components.Printer;

import java.io.IOException;


public class ServerLauncher {

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		
		Config.init();
		//TODO remove these lines
		server.startDisplayer();
		server.startReader();
		
		Printer.printDebug("Server ready");
	}

}
