package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.layers.Layer;
import it.polimi.distsys.communication.layers.secure.ServerSecureLayer;

import java.io.IOException;
import java.util.UUID;

public class ACKMessage implements Message {
	private static final long serialVersionUID = 2380820177699370771L;
	private UUID id;

	public ACKMessage(UUID id) {
		this.id = id;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public void onReceive(Layer layer) {
		layer.stopReceiving();
		ServerSecureLayer sec = (ServerSecureLayer) layer;
		try {
			sec.ACKReceived(id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onSend(Layer layer) {}

	@Override
	public Message unpack() {
		return this;
	}

}
