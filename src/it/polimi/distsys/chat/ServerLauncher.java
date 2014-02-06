package it.polimi.distsys.chat;

import it.polimi.distsys.Config;

import java.io.IOException;

public class ServerLauncher {

	public static void main(String[] args) throws IOException {
		Config.init();
		Server server = new Server();
		server.startDisplayer();

		Printer.printDebug(ServerLauncher.class, "Server ready");
		Printer.print("Server launched...");
	}

}
