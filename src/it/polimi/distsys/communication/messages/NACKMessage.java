package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;

public class NACKMessage implements Message {
	private static final long serialVersionUID = 4534419416507706053L;
	private int ID;

	public NACKMessage(int ID) {
		this.ID = ID;
	}

	@Override
	public void display() {
		System.out.println("display on " + getClass().getCanonicalName());
	}

	@Override
	public Message unpack() {
		return this;
	}

//	public void onReceive(Peer receiver, Host sender) {
//		receiver.setCommand(new RetransmissionCommand(ID));
//		receiver.onReceive(sender);
//	}

	@Override
	public void onReceive(Layer layer) {
		// TODO Auto-generated method stub
		
	}

}
