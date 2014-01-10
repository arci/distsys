package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.components.Printer;
import it.polimi.distsys.components.SequenceNumber;

public class SequenceNumberMessage implements MessageDecorator {
	private static final long serialVersionUID = -6506203353941938533L;
	private Message message;
	private SequenceNumber sn;
	
	public SequenceNumberMessage(SequenceNumber sn, Message message) {
		super();
		this.message = message;
		this.sn = sn;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), this.toString());
	}
	
	@Override
	public Message unpack() {
		return message;
	}
	
	@Override
	public String toString() {
		return "[SN: " + sn.toString() + ", " + message.toString() + "]";
	}
	
	public SequenceNumber getSn() {
		return sn;
	}

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onReceive(Layer layer) {
		//does nothing
	}

}
