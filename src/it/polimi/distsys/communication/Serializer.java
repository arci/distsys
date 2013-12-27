package it.polimi.distsys.communication;


public class Serializer implements Sender {
	private Sender sender;

	public Serializer(Sender sender) {
		super();
		this.sender = sender;
	}

	@Override
	public void send(Message msg) {
		sender.send(new RawMessage(msg.getClass().getName() + Deserializer.SEPARATOR + msg.toString()));
	}

}
