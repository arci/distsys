package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;

public class InterruptedMessage implements Message {

	private static final long serialVersionUID = -6370268141113458128L;

	@Override
	public void display() {
		System.out.println(getClass().getCanonicalName() + " received!");
	}

//	public void onReceive(Peer receiver, Host sender) {
//		sender.setActive(false);
//		receiver.setCommand(new LeaveCommand(sender.getID()));
//		receiver.onReceive(sender);
//	}

	@Override
	public Message unpack() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onReceive(Layer layer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub
		
	}

}
