package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.components.Decrypter;
import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.layers.Layer;
import it.polimi.distsys.communication.layers.secure.ClientSecureLayer;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

public class DEKMessage implements Message {
	private static final long serialVersionUID = -5806125797230332592L;
	private SealedObject dek;

	public DEKMessage(Key toEncrypt, Key kek) throws IllegalBlockSizeException,
			IOException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException {
		super();
		Cipher cipher = Cipher.getInstance(Decrypter.ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, kek);
		this.dek = new SealedObject(toEncrypt, cipher);
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public void onReceive(Layer layer) throws IOException {
		layer.stopReceiving();
		ClientSecureLayer sec = (ClientSecureLayer) layer;
		sec.updateDEK(dek);
	}

	@Override
	public void onSend(Layer layer) {
	}

	@Override
	public Message unpack() {
		return this;
	}

}
