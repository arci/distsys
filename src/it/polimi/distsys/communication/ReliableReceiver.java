package it.polimi.distsys.communication;

import java.util.ArrayList;
import java.util.List;

public class ReliableReceiver implements Receiver {
	@Override
	public List<Message> receive() {
		// TODO Auto-generated method stub
		System.out.println(getClass().getName() + " invoked!");
		
		List<Message> out = new ArrayList<Message>();
		out.add(new StringMessage("Message content"));
		return out;
	}

}
