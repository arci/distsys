package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.peers.Host;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class JoinMessage implements Message {
	private static final long serialVersionUID = 6267101796372206458L;
	private InetAddress address;
	private int port;
	private Integer ID;

	public JoinMessage(Integer ID, InetAddress address, int receptionistPort) {
		this.address = address;
		port = receptionistPort;
		this.ID = ID;
	}

	@Override
	public void display() {
		System.out.println(getClass().getCanonicalName() + " received!");
	}

	@Override
	public Message unpack() {
		return this;
	}

	@Override
	public String toString() {
		return address.getHostAddress() + ":" + port;
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		try {
			System.out.println("Joining to " + address.getHostAddress() + ":" + port);
			Host joiner = new Host(ID, receiver, new Socket(address, port));
			receiver.join(joiner);
			receiver.sendUnicast(joiner, new IDMessage(receiver.getID()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}