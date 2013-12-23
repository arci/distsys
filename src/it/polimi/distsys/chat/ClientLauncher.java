package it.polimi.distsys.chat;

import it.polimi.distsys.peers.Client;
import it.polimi.distsys.peers.Host;

import java.io.IOException;
import java.net.Socket;

public class ClientLauncher {
	public static void main(String[] args) {
		int accPort = Integer.parseInt(args[0]);
		String hostName = args[1];
		int port = Integer.parseInt(args[2]);
		try {
			Socket socket = new Socket(hostName, port);
			Client client = new Client(accPort);
			Host host = new Host(socket, "gianfranco");
			client.join(host);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
