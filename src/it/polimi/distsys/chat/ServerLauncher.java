package it.polimi.distsys.chat;

import it.polimi.distsys.Config;


public class ServerLauncher {

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		Server server = new Server(port);
		System.out.println("Server ready on port " + port);
		
		Config.init();
		server.accept();
		//TODO remove these lines
		server.startDisplayer();
		server.startReader();
	}

}
