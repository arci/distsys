package it.polimi.distsys.chat;

import java.io.IOException;

public class Server extends Peer {
	public static final Integer DEFAULT_ID = 0;
	private static Integer clientID = 1;

	public Server() throws IOException {
		super();
		ID = DEFAULT_ID;
		DEBUG = true;
		nickname = "server";
	}

	// TODO remove... Only to see things work
	public void startReader() {
		new Thread(new Reader(this)).start();
	}

	public void startDisplayer() {
		new Thread(new Displayer(this)).start();
	}

	public Integer incrementID() {
		int temp = clientID;
		clientID++;
		return temp;
	}

}
