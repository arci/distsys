package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.ApplicationLayer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.components.Printer;

public class LeaveMessage implements Message {
	private Integer leaverID;
	
	private static final long serialVersionUID = -8167138982859361874L;
	
	public LeaveMessage(Integer leaverID) {
		this.leaverID = leaverID;
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public Message unpack() {
		return this;
	}

	@Override
	public void onReceive(Layer layer) {
//		receiver.setCommand(new LeaveCommand(leaverID));
//		receiver.onReceive(sender);
		
		ApplicationLayer app = (ApplicationLayer) layer;
		app.leave(leaverID);
	}

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub
		
	}

}
