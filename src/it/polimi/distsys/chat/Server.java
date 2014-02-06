package it.polimi.distsys.chat;

import it.polimi.distsys.communication.StackFactory;

import java.io.IOException;

public class Server extends Peer {
	public Server() throws IOException {
		super();
		IS_SERVER = true;
		nickname = "server";
		stack = StackFactory.makeCompleteServerStack();
		stack.join();
	}
	
	public void startReader() {
		new Thread(new Reader(this)).start();
	}

	public void startDisplayer() {
		new Thread(new Displayer(this)).start();
	}
}
