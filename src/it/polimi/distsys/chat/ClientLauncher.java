package it.polimi.distsys.chat;

import it.polimi.distsys.peers.Client;

public class ClientLauncher {
	public static void main(String[] args) {
		int accPort = Integer.parseInt(args[0]);
		String hostName = args[1];
		int port = Integer.parseInt(args[2]);
		Client client = new Client(accPort, hostName, port);
		System.out.println("Client connected to server " + hostName + ":"
				+ port);
		System.out.println("Client listening on port " + accPort);

		client.read();
	}

}
