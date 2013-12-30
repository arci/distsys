package it.polimi.distsys.peers;

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
	private Peer coordinator;
	private Socket socket;
	private RunnableSender sender;
	private RunnableReceiver receiver;
	private MessageQueue outgoing;

	public Host(Peer coordinator, Socket socket) {
		super();
		this.coordinator = coordinator;
		this.socket = socket;
		outgoing = new MessageQueue();

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

	public void addOutgoingMessage(Message m) {
		outgoing.addMessages(new ArrayList<Message>(Arrays.asList(m)));
	}

	public List<Message> getOutgoingMessages() {
		return outgoing.getMessages();
	}

	public void addIncomingMessages(List<Message> msgs) {
		List<Message> decoratedMsgs = new ArrayList<Message>();
		for (Message m : msgs) {
			decoratedMsgs.add(new Signature(this, m));
		}
		coordinator.addIncomingMessages(decoratedMsgs);
	}

	public List<Message> getIncomingMessages() {
		return coordinator.getIncomingMessages();
	}
}
