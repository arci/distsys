package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.ReliableLayer;
import it.polimi.distsys.components.Printer;
import it.polimi.distsys.components.SequenceNumber;

import java.util.UUID;

public class ACKMessage implements Message {
	private static final long serialVersionUID = -5782706440666041170L;
	private SequenceNumber sn;

	public ACKMessage(SequenceNumber sn) {
		this.sn = sn;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public Message unpack() {
		return this;
	}

	@Override
	public void onReceive(Layer layer) {
		ReliableLayer rel = (ReliableLayer) layer;
		UUID id = sn.getClientID();
		if(rel.isMe(id)){
			rel.cleanQueue(sn.getMessageID());
		}
	}

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub
	}

}
