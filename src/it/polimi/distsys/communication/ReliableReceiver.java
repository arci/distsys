package it.polimi.distsys.communication;

import java.util.ArrayList;
import java.util.List;

public class ReliableReceiver implements Receiver {
	private Receiver receiver;

	public ReliableReceiver(Receiver receiver) {
		super();
		this.receiver = receiver;
	}

	@Override
	public List<Message> receive() {
		// TODO Auto-generated method stub
		System.out.println(getClass().getName() + " invoked!");
		
		List<Message> out = new ArrayList<Message>();
		out.add(new StringMessage("Message content"));
		return out;
	}

}
