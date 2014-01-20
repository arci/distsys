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

public class KeysLeaveMessage implements Message {
	private static final long serialVersionUID = -8148068580863352040L;
	private SealedObject[] keks;
	private SealedObject[] deks;

	public KeysLeaveMessage(Key[] enckeks, Key encdek, Key[] keks, Key[] otherkeks, Key dek)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, IOException {

		this.keks = new SealedObject[enckeks.length];
		this.deks = new SealedObject[otherkeks.length];
		Cipher kekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
		Cipher dekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
		dekCipher.init(Cipher.ENCRYPT_MODE, dek);

		//encrypting oldDEK(oldKEK(newKEK))
		for (int i = 0; i < enckeks.length; i++) {
			kekCipher.init(Cipher.ENCRYPT_MODE, keks[i]);
			this.keks[i] = new SealedObject(new SealedObject(enckeks[i], kekCipher), dekCipher);
		}

		//encrypting remainingKEKs(newDEK))
		for (int i = 0; i < otherkeks.length; i++) {
			kekCipher.init(Cipher.ENCRYPT_MODE, otherkeks[i]);
			this.deks[i] = new SealedObject(encdek, kekCipher);
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
		sec.updateOnLeave(keks, deks);
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
