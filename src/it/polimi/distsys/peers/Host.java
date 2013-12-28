package it.polimi.distsys.peers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Host {
	private Socket socket;
	private String name;
	private RunnableSender sender;
	private RunnableReceiver receiver;

	public Host(Socket socket, String name) {
		super();
		this.socket = socket;
		this.name = name;

		sender = new RunnableSender(this, null);
		receiver = new RunnableReceiver(this, null);

		new Thread(sender).start();
		new Thread(receiver).start();
	}

	public InputStream getIn() throws IOException {
		return socket.getInputStream();
	}

	public OutputStream getOut() throws IOException {
		return socket.getOutputStream();
	}

	public InetAddress getAddress() {
		return socket.getInetAddress();
	}

	public int getPort() {
		return socket.getPort();
	}
}
