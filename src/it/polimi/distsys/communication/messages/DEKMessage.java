package it.polimi.distsys.communication.messages;

import java.security.Key;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.SecureLayer;
import it.polimi.distsys.components.Printer;

public class DEKMessage implements Message {
	private static final long serialVersionUID = -8156497824267452282L;
	private Key dek;

	public DEKMessage(Key dek) {
		this.dek = dek;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public void onReceive(Layer layer) {
		layer.stopReceiving();
		SecureLayer sec = (SecureLayer) layer;
		sec.updateDEK(dek);
	}

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message unpack() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public String toString() {
		return dek.getEncoded().toString();
	}

}
