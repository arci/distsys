package it.polimi.distsys.chat;

import it.polimi.distsys.components.Host;

public interface Command {
	public void execute(Peer receiver, Host sender);
}
