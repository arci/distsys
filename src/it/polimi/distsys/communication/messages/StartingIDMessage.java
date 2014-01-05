package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.ApplicationLayer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.components.Printer;

public class StartingIDMessage implements Message {
	private Integer ID;

	private static final long serialVersionUID = -4595833754654176767L;

	public StartingIDMessage(Integer ID) {
		this.ID = ID;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass().getCanonicalName() + ": " + ID);
	}

	@Override
	public void onReceive(Layer layer) {
//		receiver.setCommand(new StartingIDCommand(ID));
//		receiver.onReceive(sender);
		ApplicationLayer app = (ApplicationLayer) layer;
		app.startingID(ID);
	}

	@Override
	public Message unpack() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub
		
	}

}
