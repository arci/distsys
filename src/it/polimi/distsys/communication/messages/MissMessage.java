package it.polimi.distsys.communication.messages;

import java.io.IOException;

import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.layers.Layer;
import it.polimi.distsys.communication.layers.reliable.ReliableLayer;

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
		try {
			int offset = (int) (Math.random() * 10);
			if (offset == 0) {
				offset++;
			}
			ReliableLayer rel = (ReliableLayer) layer;
			Printer.printDebug(getClass(), "sending with offset " + offset);
			rel.stopSending();
			try {
				rel.sendWOffset(offset, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassCastException e) {
			Printer.printDebug(
					getClass(),
					"ClassCastException on onSend(), class was: "
							+ layer.getClass());
		}
	}

	@Override
	public Message unpack() {
		return super.unpack();
	}
}
