package it.polimi.distsys.communication;

import java.net.InetAddress;

public class JoinMessage implements Message {
	private final InetAddress address;
	private final int port;

	public JoinMessage(InetAddress address, int receptionistPort) {
		this.address = address;
		port = receptionistPort;
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		System.out.println("display on " + getClass().getCanonicalName());
	}

	@Override
	public Message unpack() {
		return this;
	}

}
