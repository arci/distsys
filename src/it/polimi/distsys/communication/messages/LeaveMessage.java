package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.components.Host;

public class LeaveMessage implements Message {
	private Integer leaverID;
	
	private static final long serialVersionUID = -8167138982859361874L;
	
	public LeaveMessage(Integer leaverID) {
		this.leaverID = leaverID;
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		System.out.println("display on " + getClass().getCanonicalName());
	}

	@Override
	public Message unpack() {
		return this;
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		receiver.leave(leaverID);
	}

}
