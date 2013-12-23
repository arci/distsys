package it.polimi.distsys.peers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Host {
	private Socket socket;
	private String name;

	public Host(Socket socket, String name) {
		super();
		this.socket = socket;
		this.name = name;
	}
	
	public InputStream getIn() throws IOException {
		return socket.getInputStream();
	}

	public OutputStream getOut() throws IOException {
		return socket.getOutputStream();
	}
}
