package it.polimi.distsys.communication;

import java.util.List;

public class Deserializer implements Receiver {
	private Receiver receiver;

	public Deserializer(Receiver receiver) {
		super();
		this.receiver = receiver;
	}

	@Override
	public List<Message> receive() {
		// TODO Auto-generated method stub
		System.out.println(getClass().getName() + " invoked!");
		return receiver.receive();
	}

}
