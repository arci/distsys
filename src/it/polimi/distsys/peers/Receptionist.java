package it.polimi.distsys.peers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Receptionist implements Runnable {
	ServerSocket in;
	Server server;

	public Receptionist(ServerSocket in, Server server) {
		super();
		this.in = in;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			Socket socket = in.accept();
			server.join(new Client(socket));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
