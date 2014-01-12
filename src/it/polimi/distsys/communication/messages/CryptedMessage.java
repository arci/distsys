package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.components.Printer;

public class CryptedMessage implements Message {
	private static final long serialVersionUID = 107158341380726137L;
	private byte[] content;

	public CryptedMessage(byte[] content) {
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
		// TODO Auto-generated method stub

	}

	@Override
	public Message unpack() {
		// TODO Auto-generated method stub
		return this;
	}

}
