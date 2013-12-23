package it.polimi.distsys.peers;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class Peer {
	public static final int PORT = 1234;
	
	protected Group group;
	private Receptionist receptionist;
	private ServerSocket serverSocket;

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
		new Thread(new RunnableSender(this, host, null)).start();
		new Thread(new RunnableReceiver(this, host, null)).start();
		doStuff();
	}

	public void leave(Host host) {
		group.leave(host);
	}
	
	public void accept() {
		new Thread(receptionist).start();
	}

	protected abstract void doStuff();

}
