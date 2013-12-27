package it.polimi.distsys.peers;

import it.polimi.distsys.communication.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public abstract class Peer implements Observer {
	protected static Group group;
	protected Receptionist receptionist;
	protected ServerSocket serverSocket;
	protected static MessageQueue incoming;
	protected static MessageQueue outgoing;
	private static int sending = 0;
	private static List<Message> toSend;

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

	public static void addOutgoingMessage(Message msg) {
		List<Message> wrapper = new ArrayList<Message>();
		wrapper.add(msg);
		outgoing.addMessages(wrapper);
	}

	public static void addIncomingMessages(List<Message> msgs) {
		incoming.addMessages(msgs);
	}

	public static List<Message> getIncomingMessages() {
		return incoming.getMessages();
	}

	public synchronized static List<Message> getOutgoingMessages() {
		if (sending == group.size()) {
			toSend.clear();
		}
		
		if (toSend.isEmpty()) {
			toSend = outgoing.getMessages();
		}

		sending++;

		return toSend;
	}

	protected abstract void onJoin(Host host);

}
