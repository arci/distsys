package it.polimi.distsys.communication.secure;

import it.polimi.distsys.communication.messages.EncryptedMessage;
import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

public class Encrypter {

	private Key key;
	private Cipher cipher;

	public Encrypter() {
		super();
		try {
			cipher = Cipher.getInstance(Decrypter.ALGORITHM);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Encrypter(Key key) {
		this.key = key;
		try {
			cipher = Cipher.getInstance(Decrypter.ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, this.key);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setKey(Key newKey) {
		this.key = newKey;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, this.key);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public EncryptedMessage encryptMsg(Message m) throws IllegalBlockSizeException, IOException {
		return new EncryptedMessage(m, cipher);
	}
	
	public SealedObject encrypt(Serializable o, Key k) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, IOException{
		Cipher cipher = Cipher.getInstance(Decrypter.ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return new SealedObject(o, cipher);
	}

}
