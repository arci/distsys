package it.polimi.distsys.communication;

public class SequenceNumberMessage implements MessageDecorator {
	private Message message;
	int id;

	public SequenceNumberMessage(Message message, int id) {
		super();
		this.message = message;
		this.id = id;
	}

	@Override
	public void display() {
		
		System.out.println("display on " + getClass().getCanonicalName());
	}

	@Override
	public Message unpack() {
		return message;
	}

	@Override
	public String toString() {
		return "SequenceNumber [message=" + message + ", id=" + id + "]";
	}

}
