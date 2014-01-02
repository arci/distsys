package it.polimi.distsys.chat;

import it.polimi.distsys.chat.commands.Command;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.components.Group;
import it.polimi.distsys.components.Host;
import it.polimi.distsys.components.MessageQueue;
import it.polimi.distsys.components.Receptionist;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Iterator;
import java.util.List;

public abstract class Peer {
	protected Integer ID;
	protected Group group;
	protected Receptionist receptionist;
	protected ServerSocket serverSocket;
	protected MessageQueue incoming;
	protected Command command;

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
	
	public void setCommand(Command command) {
		this.command = command;
	}
	
	public void onReceive(Host sender){
		command.execute(this, sender);
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
			if (!receiver.equals(host)) {
				// TODO remove println
				System.out.println("SendExceptOne to " + receiver.getAddress()
						+ ":" + receiver.getPort());
				receiver.addOutgoingMessage(msg);
			}
		}
	}

	public InetAddress getAddress() {
		return serverSocket.getInetAddress();
	}

	public int getListeningPort() {
		return serverSocket.getLocalPort();
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public Group getGroup() {
		return group;
	}
}
