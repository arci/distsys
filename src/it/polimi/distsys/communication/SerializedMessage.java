package it.polimi.distsys.communication;

public class SerializedMessage implements MessageDecorator {

	private Message message;
	private String serialization;

	public SerializedMessage(Message message) {
		this.message = message;
		this.serialization = message.toString();
	}

	@Override
	public void display() {
		System.out.println("display on"+ getClass().getCanonicalName() + serialization);

	}

	@Override
	public Message unpack() {

		return message;
	}

	@Override
	public String toString() {
		return "SerializedMessage ["+serialization+"]";
	}

}
