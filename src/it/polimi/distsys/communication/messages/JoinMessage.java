package it.polimi.distsys.communication.messages;

import it.polimi.distsys.peers.Host;
import it.polimi.distsys.peers.Peer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class JoinMessage implements Message {
	private static final long serialVersionUID = 6267101796372206458L;
	private InetAddress address;
	private int port;

	public JoinMessage(InetAddress address, int receptionistPort) {
		this.address = address;
		port = receptionistPort;
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
			receiver.join(new Host(receiver, new Socket(address, port)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
