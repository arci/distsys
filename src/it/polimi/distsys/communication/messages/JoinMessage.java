package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.components.TableException;
import it.polimi.distsys.communication.layers.Layer;
import it.polimi.distsys.communication.layers.secure.SecureLayer;

import java.io.IOException;
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
	public void onReceive(Layer layer) throws IOException {
		layer.stopReceiving();
		SecureLayer sec = (SecureLayer) layer;
		try {
			sec.join(id);
		} catch (TableException e) {
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
