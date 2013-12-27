package it.polimi.distsys.peers;

import it.polimi.distsys.communication.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Host implements Observable {
	private Socket socket;
	private String name;
	private RunnableSender sender;
	private RunnableReceiver receiver;
	private List<Observer> observers;

	public Host(Socket socket, String name) {
		super();
		this.socket = socket;
		this.name = name;

		observers = new ArrayList<Observer>();
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

	@Override
	public void register(Observer o) {
		observers.add(o);
	}

	@Override
	public void unregister(Observer o) {
		observers.remove(o);
	}

	public void notifyObservers(Message m) {
		for(Observer o : observers){
			o.update(m);
		}
	}
}
