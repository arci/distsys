package it.polimi.distsys.components;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.Stack;
import it.polimi.distsys.communication.factories.StackFactory;
import it.polimi.distsys.communication.messages.LeaveMessage;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.Signature;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Host {
	private Integer ID;
	private Peer coordinator;
	private Socket socket;
	private RunnableSender sender;
	private RunnableReceiver receiver;
	private MessageQueue outgoing;
	private boolean active;

	public Host(Integer ID, Peer coordinator, Socket socket) {
		super();
		active = true;
		this.ID = ID;
		this.coordinator = coordinator;
		this.socket = socket;
		outgoing = new MessageQueue();

		try {
			Stack stack = StackFactory.makeTCPStack(getIn(), getOut());
			sender = new RunnableSender(this, stack,  null);
			receiver = new RunnableReceiver(this, stack, null);

			new Thread(sender).start();
			new Thread(receiver).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public void addOutgoingMessage(Message m) {
		outgoing.addMessages(new ArrayList<Message>(Arrays.asList(m)));
	}

	public List<Message> getOutgoingMessages() {
		return outgoing.getMessages();
	}

	public void addIncomingMessages(List<Message> msgs) {
		List<Message> decoratedMsgs = new ArrayList<Message>();
		for (Message m : msgs) {
			if (m == null) {
				m = new LeaveMessage(getID());
			}
			decoratedMsgs.add(new Signature(this, m));
		}
		coordinator.addIncomingMessages(decoratedMsgs);
	}

	public List<Message> getIncomingMessages() {
		return coordinator.getIncomingMessages();
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public Integer getID() {
		return ID;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
