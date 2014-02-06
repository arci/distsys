package it.polimi.distsys.communication.messages;

import java.io.IOException;

import it.polimi.distsys.chat.Printer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.reliable.ReliableLayer;
import it.polimi.distsys.communication.reliable.SequenceNumber;

public class NACKMessage implements Message {
	private static final long serialVersionUID = 4534419416507706053L;
	private SequenceNumber sn;

	public NACKMessage(SequenceNumber sn) {
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
		try {
			ReliableLayer rel = (ReliableLayer) layer;
			rel.stopReceiving();
			Printer.printDebug(getClass(), "stopping NACK sending on " + sn.getClientID());
			rel.stopNACK(sn.getClientID());
			if (rel.isMe(sn.getClientID())) {
				rel.resend(sn);
			}
		} catch (ClassCastException | IOException e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub

	}

}
