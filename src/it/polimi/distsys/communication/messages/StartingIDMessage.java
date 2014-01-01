package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.chat.actions.StartingIDCommand;
import it.polimi.distsys.components.Host;

public class StartingIDMessage implements Message {
	private Integer ID;

	private static final long serialVersionUID = -4595833754654176767L;

	public StartingIDMessage(Integer ID) {
		this.ID = ID;
	}

	@Override
	public void display() {
		System.out.println(getClass().getCanonicalName() + ": " + ID);
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		receiver.setCommand(new StartingIDCommand(ID));
		receiver.onReceive(sender);
		System.out.println("My ID is now " + receiver.getID());
	}

	@Override
	public Message unpack() {
		// TODO Auto-generated method stub
		return this;
	}

}
