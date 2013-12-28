package it.polimi.distsys.peers;

import it.polimi.distsys.communication.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public abstract class Peer {
	protected Group group;
	protected Receptionist receptionist;
	protected ServerSocket serverSocket;
	protected MessageQueue incoming;
	protected MessageQueue outgoing;
	private int sending = 0;
	private List<Message> toSend;

	public Peer(int port) {
		super();
		group = new Group();
		incoming = new MessageQueue();
		outgoing = new MessageQueue();
		toSend = new ArrayList<Message>();
		try {
			serverSocket = new ServerSocket(port);
			receptionist = new Receptionist(serverSocket, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void join(Host host) {
		group.join(host);
		onJoin(host);
	}

	public void leave(Host host) {
		group.leave(host);
	}

	public void accept() {
		new Thread(receptionist).start();
	}

	public void addOutgoingMessage(Message msg) {
		List<Message> wrapper = new ArrayList<Message>();
		wrapper.add(msg);
		outgoing.addMessages(wrapper);
	}

	public void addIncomingMessages(List<Message> msgs) {
		incoming.addMessages(msgs);
	}

	public List<Message> getIncomingMessages() {
		return incoming.getMessages();
	}

	public synchronized List<Message> getOutgoingMessages() {
		if (sending == group.size()) {
			toSend.clear();
			sending = 0;
		}
		
		if (toSend.isEmpty()) {
			toSend = outgoing.getMessages();
		}

		sending++;

		return toSend;
	}
	
	public boolean isMe(InetAddress address, int port){
		return address.equals(serverSocket.getInetAddress()) && port == serverSocket.getLocalPort();
	}
	
	public abstract void onJoin(Host host);

}
