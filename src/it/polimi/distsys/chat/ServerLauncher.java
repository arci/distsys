package it.polimi.distsys.chat;

import it.polimi.distsys.peers.Server;

public class ServerLauncher {
	
	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		Server server = new Server(port);
		System.out.println("Server ready");
		server.accept();
	}

}
