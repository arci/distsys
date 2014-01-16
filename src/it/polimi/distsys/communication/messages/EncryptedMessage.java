package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.layers.Layer;

public class EncryptedMessage implements Message {
	private static final long serialVersionUID = 107158341380726137L;
	private byte[] content;

	public EncryptedMessage(byte[] content) {
		super();
		this.content = content;
	}

	public byte[] getContent() {
		return content;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), content.toString());
	}

	@Override
	public void onReceive(Layer layer) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSend(Layer layer) {
	}

	@Override
	public Message unpack() {
		// TODO Auto-generated method stub
		return this;
	}

}
