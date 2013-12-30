package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.chat.Server;
import it.polimi.distsys.peers.Host;

import java.net.InetAddress;

public class ConnectionMessage implements Message {
	private static final long serialVersionUID = 6267101796372206458L;
	private InetAddress address;
	private int port;

	public ConnectionMessage(InetAddress address, int receptionistPort) {
		this.address = address;
		port = receptionistPort;
	}

	@Override
	public void display() {
		System.out.print(getClass().getCanonicalName() + " received:  ");
		System.out.print(this.toString() + "\n");
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
		Server server = (Server) receiver;
		Integer ID = server.incrementID(); 
		server.sendUnicast(sender, new StartingIDMessage(ID));
		server.join(sender);
		server.sendExceptOne(sender, new JoinMessage(ID, address, port));
	}
}