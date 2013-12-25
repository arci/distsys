package it.polimi.distsys.communication;

import it.polimi.distsys.peers.Host;

public class ReliableSender implements Sender {
	private Sender sender;
	private int clientID;
	
	public ReliableSender(Sender sender, int clientID) {
		super();
		this.sender = sender;
		this.clientID=clientID;
	}

	@Override
	public void send(Host host, Message msg) {
		// TODO Auto-generated method stub
		
		Message reliableMsg = new SequenceNumberMessage(msg, clientID);
		sender.send(host, reliableMsg);
	}

}
