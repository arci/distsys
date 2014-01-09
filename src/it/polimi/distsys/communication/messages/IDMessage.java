package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.ApplicationLayer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.components.Printer;

public class IDMessage implements Message {

	private static final long serialVersionUID = -4595833754654176767L;
	private Integer ID;

	public IDMessage(Integer ID) {
		this.ID = ID;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), ID.toString());
	}

	@Override
	public void onReceive(Layer layer) {
		// receiver.setCommand(new IDCommand(ID));
		// receiver.onReceive(sender);
		// System.out.println("Host " + sender.getAddress().getHostAddress() +
		// ":"
		// + sender.getPort() + " has ID " + sender.getID());

		ApplicationLayer app = (ApplicationLayer) layer;
		app.id(ID);
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
