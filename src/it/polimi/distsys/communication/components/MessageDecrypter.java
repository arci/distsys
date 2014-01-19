package it.polimi.distsys.communication.components;

import it.polimi.distsys.communication.messages.EncryptedMessage;
import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MessageDecrypter {
	public static final String ALGORITHM = "AES";
	private Key key;
	private Cipher cipher;

	public MessageDecrypter() {
		super();
		try {
			this.cipher = Cipher.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MessageDecrypter(Key key) {
		this.key = key;
		try {
			this.cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, this.key);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateKey(Key newKey) {
		this.key = newKey;
		try {
			cipher.init(Cipher.DECRYPT_MODE, this.key);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Message decrypt(EncryptedMessage m) throws ClassNotFoundException,
			IllegalBlockSizeException, BadPaddingException, IOException {
		return m.getContent(cipher);
	}

}
