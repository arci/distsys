package it.polimi.distsys.chat;

import java.io.IOException;

public class Client extends Peer {

	public Client() throws IOException {
		super();
		//DEBUG = false;
	}

	public void startReader() {
		new Thread(new AutomaticReader(this)).start();
	}

	public void startDisplayer() {
		new Thread(new Displayer(this)).start();
	}
}