package it.polimi.distsys.peers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public abstract class Peer {
	protected Group group;
	protected Socket socket;

	public Peer(Socket socket) {
		super();
		group = new Group();
		this.socket = socket;
	}

	final public void join(Peer peer) {
		group.join(peer);
		new Thread(new RunnableSender(this, peer, null)).start();
		new Thread(new RunnableReceiver(this, peer, null)).start();
		doStuff();
	}

	public void leave(Peer peer) {
		group.leave(peer);
	}

	public InputStream getIn() throws IOException {
		return socket.getInputStream();
	}

	public OutputStream getOut() throws IOException {
		return socket.getOutputStream();
	}

	public abstract void doStuff();

}
