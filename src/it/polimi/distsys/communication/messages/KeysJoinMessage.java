package it.polimi.distsys.communication.messages;

import it.polimi.distsys.Printer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.secure.ClientSecureLayer;
import it.polimi.distsys.communication.secure.Decrypter;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

public class KeysJoinMessage implements Message {
	private static final long serialVersionUID = -4459093759662524595L;
	private SealedObject[] keks;
	private SealedObject dek;

	public KeysJoinMessage(Key[] enckeks, Key encdek, Key[] keks, Key dek)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, IOException {
		
		this.keks = new SealedObject[enckeks.length];
		Cipher cipher = Cipher.getInstance(Decrypter.ALGORITHM);
		
		for (int i = 0; i < enckeks.length; i++) {
			cipher.init(Cipher.ENCRYPT_MODE, keks[i]);
			this.keks[i] = new SealedObject(enckeks[i], cipher);
		}

		cipher.init(Cipher.ENCRYPT_MODE, dek);
		this.dek = new SealedObject(encdek, cipher);
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public void onReceive(Layer layer) throws IOException {
		layer.stopReceiving();
		ClientSecureLayer sec = (ClientSecureLayer) layer;
		sec.updateOnJoin(keks, dek);
		sec.keysReceived();
	}

	@Override
	public void onSend(Layer layer) {
	}

	@Override
	public Message unpack() {
		return this;
	}

}
