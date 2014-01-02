package it.polimi.distsys.chat.commands;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.components.Host;

public class LeaveCommand implements Command {
	private Integer leaverID;

	public LeaveCommand(Integer leaverID) {
		this.leaverID = leaverID;
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		receiver.getGroup().leave(leaverID);
		// TODO remove
		System.out.println("My group is: " + receiver.getGroup().toString());
	}

}
