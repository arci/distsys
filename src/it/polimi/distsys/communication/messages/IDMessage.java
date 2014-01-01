package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.IDCommand;
import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.components.Host;

public class IDMessage implements Message {

	private static final long serialVersionUID = -4595833754654176767L;
	private Integer ID;

	public IDMessage(Integer ID) {
		this.ID = ID;
	}

	@Override
	public void display() {
		System.out.println(getClass().getCanonicalName() + ": " + ID);
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		receiver.setCommand(new IDCommand(ID));
		receiver.onReceive(sender);
		System.out.println("Host " + sender.getAddress().getHostAddress() + ":"
				+ sender.getPort() + " has ID " + sender.getID());
	}

	@Override
	public Message unpack() {
		// TODO Auto-generated method stub
		return this;
	}

}
