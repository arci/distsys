package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;

public class SequenceNumberMessage implements MessageDecorator {
	private static final long serialVersionUID = -6506203353941938533L;
	private Message message;
	int ID;

	public SequenceNumberMessage(int ID, Message message) {
		super();
		this.message = message;
		this.ID = ID;
	}

	@Override
	public void display() {
		System.out.println(this.toString());
		message.display();
	}

	@Override
	public void onReceive(Layer layer) {
		//does nothing
	}
	
	@Override
	public Message unpack() {
		return message;
	}
	
	@Override
	public String toString() {
		return "[SN: " + ID + ", " + message.toString() + "]";
	}

	public int getID() {
		return ID;
	}

}
