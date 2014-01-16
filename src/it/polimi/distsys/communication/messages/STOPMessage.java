package it.polimi.distsys.communication.messages;

import java.io.IOException;

import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.layers.Layer;
import it.polimi.distsys.communication.layers.secure.ClientSecureLayer;

public class STOPMessage implements Message {
	private static final long serialVersionUID = 305630151031207741L;

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public void onReceive(Layer layer) {
		layer.stopReceiving();
		ClientSecureLayer sec = (ClientSecureLayer) layer;
		try {
			sec.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onSend(Layer layer) {

	}

	@Override
	public Message unpack() {
		return this;
	}

}
