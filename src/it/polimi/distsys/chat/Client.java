package it.polimi.distsys.chat;

import it.polimi.distsys.communication.StackFactory;

import java.io.IOException;

public class Client extends Peer {

	public Client() throws IOException {
		super();
		stack = StackFactory.makeCompleteClientStack();
		stack.join();
	}

	public void startReader() {
		new Thread(new Reader(this)).start();
	}

	public void startDisplayer() {
		new Thread(new Displayer(this)).start();
	}
}