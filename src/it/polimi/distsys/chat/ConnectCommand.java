package it.polimi.distsys.chat;

import it.polimi.distsys.communication.messages.JoinMessage;
import it.polimi.distsys.communication.messages.StartingIDMessage;
import it.polimi.distsys.components.Host;

import java.net.InetAddress;

public class ConnectCommand implements Command {
	private InetAddress address;
	private int port;

	public ConnectCommand(InetAddress address, int receptionistPort) {
		this.address = address;
		port = receptionistPort;
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		Server server = (Server) receiver;
		Integer ID = server.incrementID(); 
		server.sendUnicast(sender, new StartingIDMessage(ID));
		server.getGroup().join(sender);
		server.getGroup().setMemberID(sender, ID);
		server.sendExceptOne(sender, new JoinMessage(ID, address, port));	
	}

}
