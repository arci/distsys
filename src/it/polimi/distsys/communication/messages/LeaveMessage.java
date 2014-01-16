package it.polimi.distsys.communication.messages;

import java.util.UUID;

import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.layers.Layer;
import it.polimi.distsys.communication.layers.secure.SecureLayer;

public class LeaveMessage implements Message {
	private static final long serialVersionUID = 3236812439773654713L;
	private UUID id;

	public LeaveMessage(UUID id) {
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
		sec.leave(id);
	}

	@Override
	public void onSend(Layer layer) {}

	@Override
	public Message unpack() {
		return this;
	}

}
