package it.polimi.distsys.peers;

import java.net.Socket;

public class Client extends Peer {

	public Client(Socket socket) {
		super(socket);
	}
	
	@Override
	public void doStuff() {}
}
