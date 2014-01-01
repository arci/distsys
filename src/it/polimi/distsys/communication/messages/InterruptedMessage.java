package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.chat.actions.LeaveCommand;
import it.polimi.distsys.components.Host;

public class InterruptedMessage implements Message {

	private static final long serialVersionUID = -6370268141113458128L;

	@Override
	public void display() {
		System.out.println(getClass().getCanonicalName() + " received!");
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		sender.setActive(false);
		receiver.setCommand(new LeaveCommand(sender.getID()));
		receiver.onReceive(sender);
	}

	@Override
	public Message unpack() {
		// TODO Auto-generated method stub
		return null;
	}

}
