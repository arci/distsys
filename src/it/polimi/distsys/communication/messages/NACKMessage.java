package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.ReliableLayer;
import it.polimi.distsys.components.Printer;

public class NACKMessage implements Message {
	private static final long serialVersionUID = 4534419416507706053L;
	private int ID;

	public NACKMessage(int ID) {
		this.ID = ID;
	}

	@Override
	public void display() {
		Printer.printDebug("display on " + getClass().getCanonicalName());
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
		try {
			ReliableLayer rel = (ReliableLayer) layer;
			rel.resend(ID);
		} catch (ClassCastException e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub
		
	}

}
