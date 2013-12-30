package it.polimi.distsys.peers;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Iterator;
import java.util.List;

public abstract class Peer {
	protected Group group;
	protected Receptionist receptionist;
	protected ServerSocket serverSocket;
	protected MessageQueue incoming;

	public Peer(int port) {
		super();
		group = new Group();
		incoming = new MessageQueue();
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

	public void addIncomingMessages(List<Message> msgs) {
		incoming.addMessages(msgs);
	}

	public List<Message> getIncomingMessages() {
		return incoming.getMessages();
	}

	public void sendUnicast(Host host, Message msg) {
		host.addOutgoingMessage(msg);
	}

	public void sendMulticast(Message msg) {
		Iterator<Host> itr = group.iterator();

		while (itr.hasNext()) {
			itr.next().addOutgoingMessage(msg);
		}
	}

	public void sendExceptOne(Host host, Message msg) {
		Iterator<Host> itr = group.iterator();

		while (itr.hasNext()) {
			Host receiver = itr.next();
			if(!receiver.equals(host)){
				receiver.addOutgoingMessage(msg);
			}
		}
	}

	public abstract void onJoin(Host host);

}
