package it.polimi.distsys.peers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Peer {
	public static final int PORT = 1234;

	private Receptionist receptionist;
	private ServerSocket serverSocket;

	public Server(Socket socket) {
		super(socket);
		try {
			serverSocket = new ServerSocket(PORT);
			receptionist = new Receptionist(serverSocket, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void accept(){
		new Thread(receptionist).start();
	}
	
	@Override
	public void leave(Peer peer) {
		super.leave(peer);
		//TODO implement our algorithm
	}

	@Override
	public void doStuff() {
		//TODO send to everybody the joint peer		
	}
}
