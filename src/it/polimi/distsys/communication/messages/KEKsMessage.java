package it.polimi.distsys.communication.messages;

import java.security.Key;
import java.util.List;
import java.util.UUID;

import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.layers.Layer;
import it.polimi.distsys.communication.layers.secure.SecureLayer;

public class KEKsMessage implements Message {
	private static final long serialVersionUID = -2283156939799401703L;
	private List<Key> keks;
	private UUID receiver;

	public KEKsMessage(UUID receiver, List<Key> keks) {
		this.keks = keks;
		this.receiver = receiver;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public void onReceive(Layer layer) {
		layer.stopReceiving();
		SecureLayer sec = (SecureLayer) layer;
		if(sec.isForMe(receiver)){
			sec.updateKEKs(keks);
		}
	}

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message unpack() {
		return this;
	}

}
