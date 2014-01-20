package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.components.TableException;
import it.polimi.distsys.communication.layers.Layer;
import it.polimi.distsys.communication.layers.secure.ServerSecureLayer;

import java.io.IOException;
import java.util.UUID;

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
	public void onReceive(Layer layer) throws IOException {
		layer.stopReceiving();
		try {
			ServerSecureLayer sec = (ServerSecureLayer) layer;
			try {
				sec.leave(id);
			} catch (TableException e) {
				e.printStackTrace();
			}
		} catch (ClassCastException e) {

		}
	}

	@Override
	public void onSend(Layer layer) {}

	@Override
	public Message unpack() {
		return this;
	}

}
