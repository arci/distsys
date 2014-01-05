package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.ReliableLayer;
import it.polimi.distsys.components.Printer;

public class MissMessage extends StringMessage implements Message {
	private static final long serialVersionUID = -8644231993272185224L;

	public MissMessage(String content) {
		super(content);
	}

	@Override
	public void display() {
		super.display();
	}

	@Override
	public void onReceive(Layer layer) {
		super.onReceive(layer);
	}

	@Override
	public void onSend(Layer layer) {
		int offset = (int) (Math.random() * 10);
		try {
			ReliableLayer rel = (ReliableLayer) layer;
			Printer.printDebug(getClass().getCanonicalName()
					+ " sending with offset " + offset);
			rel.stopSending();
			rel.sendWOffset(offset, this);
		} catch (ClassCastException e) {
			Printer.printDebug("Classcastexception on onSend(), class was: " + layer.getClass());
		}
	}

	@Override
	public Message unpack() {
		return super.unpack();
	}
}
