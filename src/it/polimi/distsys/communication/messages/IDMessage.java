package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.peers.Host;

public class IDMessage implements Message {
	private Integer ID;

	private static final long serialVersionUID = -4595833754654176767L;

	public IDMessage(Integer ID) {
		this.ID = ID;
	}

	@Override
	public void display() {
		System.out.println(getClass().getCanonicalName() + ": " + ID);
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		sender.setID(ID);
		receiver.join(sender);
		System.out.println("Host " + sender.getAddress().getHostAddress() + ":" + sender.getPort() + " has ID " + sender.getID());
	}

	@Override
	public Message unpack() {
		// TODO Auto-generated method stub
		return this;
	}

}
