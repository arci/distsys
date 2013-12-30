package it.polimi.distsys.chat;

import it.polimi.distsys.peers.Host;
import it.polimi.distsys.peers.Peer;

public class Server extends Peer {

	public Server(int port) {
		super(port);
	}

	@Override
	public void leave(Host host) {
		// TODO Auto-generated method stub
		super.leave(host);
	}

	// TODO remove... Only to see things work
	public void startReader() {
		new Thread(new Reader(this)).start();
	}

	public void startDisplayer() {
		new Thread(new Displayer(this)).start();
	}
}
