package it.polimi.distsys.peers;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class Peer implements Observer {
	protected Group group;
	protected Receptionist receptionist;
	protected ServerSocket serverSocket;

	public Peer(int port) {
		super();
		group = new Group();
		try {
			serverSocket = new ServerSocket(port);
			receptionist = new Receptionist(serverSocket, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	final public void join(Host host) {
		group.join(host);
		host.register(this);
		onJoin(host);
	}

	public void leave(Host host) {
		group.leave(host);
	}

	public void accept() {
		new Thread(receptionist).start();
	}

	protected abstract void onJoin(Host host);

}
