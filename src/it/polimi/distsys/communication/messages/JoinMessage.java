package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.SecureLayer;
import it.polimi.distsys.components.Printer;

import java.util.UUID;

public class JoinMessage implements Message {
	private static final long serialVersionUID = 8037414378256193328L;
	private UUID id;

	public JoinMessage(UUID id) {
		super();
		this.id = id;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public void onReceive(Layer layer) {
		layer.stopReceiving();
		SecureLayer sec = (SecureLayer) layer;
		sec.join(id);
	}

	@Override
	public void onSend(Layer layer) {

	}

	@Override
	public Message unpack() {
		return this;
	}

}
