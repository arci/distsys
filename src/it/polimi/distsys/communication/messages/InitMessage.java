package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Printer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.secure.ClientSecureLayer;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

public class InitMessage implements Message {
	private static final long serialVersionUID = 3758551565395910387L;
	private SealedObject[] keks;
	private SealedObject dek;
	private UUID receiver;

	public InitMessage(UUID receiver, Key[] keks, Key dek, Key publicKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, IOException {
		this.receiver = receiver;
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		this.keks = new SealedObject[keks.length];
		for (int i = 0; i < keks.length; i++) {
			this.keks[i] = new SealedObject(keks[i], cipher);
		}
		this.dek = new SealedObject(dek, cipher);
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public void onReceive(Layer layer) throws IOException {
		layer.stopReceiving();
		ClientSecureLayer sec = (ClientSecureLayer) layer;
		if(sec.isForMe(receiver)){
			sec.init(keks, dek);
			sec.keysReceived();
		}
	}

	@Override
	public void onSend(Layer layer) {}

	@Override
	public Message unpack() {
		return this;
	}

}
