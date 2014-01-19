package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.components.TableException;
import it.polimi.distsys.communication.layers.Layer;
import it.polimi.distsys.communication.layers.secure.SecureLayer;

import java.io.IOException;
import java.security.Key;
import java.util.UUID;

public class JoinMessage implements Message {
	private static final long serialVersionUID = 8037414378256193328L;
	private UUID id;
	private Key publicKey;

	public JoinMessage(UUID id, Key publicKey) {
		super();
		this.id = id;
		this.publicKey = publicKey;
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
			sec.join(id, publicKey);
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
