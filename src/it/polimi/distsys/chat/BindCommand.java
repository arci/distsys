package it.polimi.distsys.chat;

import it.polimi.distsys.components.Host;

import java.net.Socket;

public class BindCommand implements Command {
	Socket socket;

	public BindCommand(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		receiver.getGroup().join(new Host(null, receiver, socket));
	}

}
