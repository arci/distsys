package it.polimi.distsys.communication.components;

import it.polimi.distsys.communication.messages.EncryptedMessage;
import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MessageEncrypter {

	private Key key;
	private Cipher cipher;

	public MessageEncrypter() {
		super();
		try {
			cipher = Cipher.getInstance(MessageDecrypter.ALGORITHM);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MessageEncrypter(Key key) {
		this.key = key;
		try {
			cipher = Cipher.getInstance(MessageDecrypter.ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, this.key);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateKey(Key newKey) {
		this.key = newKey;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, this.key);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public EncryptedMessage encrypt(Message m) throws IllegalBlockSizeException, IOException {
		return new EncryptedMessage(m, cipher);
	}

}
