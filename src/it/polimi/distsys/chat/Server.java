package it.polimi.distsys.chat;

import java.io.IOException;

public class Server extends Peer {
	public Server() throws IOException {
		super();
		DEBUG = true;
		nickname = "server";
		IS_SERVER = true;
	}

	// TODO remove... Only to see things work
	public void startReader() {
		new Thread(new Reader(this)).start();
	}

	public void startDisplayer() {
		new Thread(new Displayer(this)).start();
	}
}
