package it.polimi.distsys.chat.commands;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.components.Host;

public interface Command {
	public void execute(Peer receiver, Host sender);
}
