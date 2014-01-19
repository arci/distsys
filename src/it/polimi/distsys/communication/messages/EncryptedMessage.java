package it.polimi.distsys.communication.messages;

import java.io.IOException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;

import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.layers.Layer;

public class EncryptedMessage implements Message {
	private static final long serialVersionUID = 107158341380726137L;
	private SealedObject content;

	public EncryptedMessage(Message m, Cipher c)
			throws IllegalBlockSizeException, IOException {
		super();
		this.content = new SealedObject(m, c);
	}

	public Message getContent(Cipher c) throws ClassNotFoundException,
			IllegalBlockSizeException, BadPaddingException, IOException {
		return (Message) content.getObject(c);
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), content.toString());
	}

	@Override
	public void onReceive(Layer layer) {
	}

	@Override
	public void onSend(Layer layer) {
	}

	@Override
	public Message unpack() {
		return this;
	}

}
