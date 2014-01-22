package it.polimi.distsys.chat;

import it.polimi.distsys.communication.layers.StackFactory;

import java.io.IOException;

public class Server extends Peer {
	public Server() throws IOException {
		super();
		IS_SERVER = true;
		nickname = "server";
		stack = StackFactory.makeCompleteServerStack();
		stack.join();
	}

	public void startDisplayer() {
		new Thread(new Displayer(this)).start();
	}
}
