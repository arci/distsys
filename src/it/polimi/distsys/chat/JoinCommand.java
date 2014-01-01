package it.polimi.distsys.chat;

import it.polimi.distsys.communication.messages.IDMessage;
import it.polimi.distsys.components.Host;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class JoinCommand implements Command {
	private InetAddress address;
	private int port;
	private Integer ID;

	public JoinCommand(Integer ID, InetAddress address, int receptionistPort) {
		this.address = address;
		port = receptionistPort;
		this.ID = ID;
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		try {
			Host joiner = new Host(ID, receiver, new Socket(address, port));
			receiver.getGroup().join(joiner);
			//TODO remove
			System.out.println("My group is now: " + receiver.getGroup().toString());
			receiver.sendUnicast(joiner, new IDMessage(receiver.getID()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
