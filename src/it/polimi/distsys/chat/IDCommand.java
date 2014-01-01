package it.polimi.distsys.chat;

import it.polimi.distsys.components.Host;

public class IDCommand implements Command {
	private Integer ID;

	public IDCommand(Integer ID) {
		this.ID = ID;
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		Client client = (Client) receiver;
		client.getGroup().setMemberID(sender, ID);
	}

}
