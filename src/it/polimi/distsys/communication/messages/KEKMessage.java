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

public class KEKMessage implements Message {
	private static final long serialVersionUID = 5951435959721765624L;
	private SealedObject kek;

	public KEKMessage(Key toEncrypt, Key kek, Key dek)
			throws IllegalBlockSizeException, IOException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException {
		super();
		Cipher dekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
		Cipher kekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
		kekCipher.init(Cipher.ENCRYPT_MODE, kek);
		if (dek == null) {
			this.kek = new SealedObject(toEncrypt, kekCipher);
		} else {
			dekCipher.init(Cipher.ENCRYPT_MODE, dek);
			this.kek = new SealedObject(new SealedObject(toEncrypt, kekCipher), dekCipher);
		}
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public void onReceive(Layer layer) throws IOException {
		layer.stopReceiving();
		ClientSecureLayer sec = (ClientSecureLayer) layer;
		sec.updateKEK(kek);
	}

	@Override
	public void onSend(Layer layer) {
	}

	@Override
	public Message unpack() {
		return this;
	}

}
