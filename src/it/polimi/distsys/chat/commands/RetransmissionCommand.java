package it.polimi.distsys.chat.commands;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.components.Host;

public class RetransmissionCommand implements Command {
	private int ID;

	public RetransmissionCommand(int ID) {
		this.ID = ID;
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		receiver.sendUnicast(sender, null);
	}

}
