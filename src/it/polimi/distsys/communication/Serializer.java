package it.polimi.distsys.communication;

public class Serializer implements Sender {
	private Sender sender;

	public Serializer(Sender sender) {
		super();
		this.sender = sender;
	}

	@Override
	public void send(Host host, Message msg) {
		// TODO Auto-generated method stub
		System.out
				.println(getClass().getName() + " sending: " + msg.toString());
		sender.send(host, msg);
	}

}
