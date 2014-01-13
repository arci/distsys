package it.polimi.distsys.chat;

import it.polimi.distsys.communication.StackFactory;

import java.io.IOException;

public class Server extends Peer {
	public Server() throws IOException {
		super();
		DEBUG = true;
		IS_SERVER = true;
		nickname = "server";
		stack = StackFactory.makeCompleteServerStack();
		stack.join();
	}

	// TODO remove... Only to see things work
	public void startReader() {
		new Thread(new Reader(this)).start();
	}

	public void startDisplayer() {
		new Thread(new Displayer(this)).start();
	}
}
