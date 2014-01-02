package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.SequenceNumberMessage;

public class ReliableSender implements Sender {
	private int ID;
	private Sender sender;

	public ReliableSender(Sender sender) {
		super();
		ID = 0;
		this.sender = sender;
	}

	@Override
	public void send(Message msg) {
		ID++;
		Message toSend = new SequenceNumberMessage(ID, msg);
		sender.send(toSend);
	}

}
