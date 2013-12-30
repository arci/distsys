package it.polimi.distsys.peers;

import it.polimi.distsys.communication.messages.JoinMessage;

public class Server extends Peer {

	public Server(int port) {
		super(port);
	}

	@Override
	public void leave(Host host) {
		// TODO Auto-generated method stub
		super.leave(host);
	}

	@Override
	public void onJoin(Host host) {
		sendExceptOne(host, new JoinMessage(host.getAddress(), host.getPort()));
	}
	
	//TODO remove... Only to see things work
	public void startReader() {
		new Thread(new Reader(this)).start();
	}

	public void startDisplayer() {
		new Thread(new Displayer(this)).start();
	}
}
