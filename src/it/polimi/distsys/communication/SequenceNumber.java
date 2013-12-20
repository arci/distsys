package it.polimi.distsys.communication;

public class SequenceNumber implements MessageDecorator {
	private Message message;
	int id;

	private SequenceNumber(Message message, int id) {
		super();
		this.message = message;
		this.id = id;
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
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
