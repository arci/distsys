package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.chat.commands.JoinCommand;
import it.polimi.distsys.components.Host;

import java.net.InetAddress;

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
		System.out.println("Joining client" + ID + " on " + address.getHostAddress() + ":"
				+ port);
		receiver.setCommand(new JoinCommand(ID, address, port));
		receiver.onReceive(sender);
	}
}
