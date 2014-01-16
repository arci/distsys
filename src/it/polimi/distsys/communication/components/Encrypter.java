package it.polimi.distsys.communication.components;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Encrypter {

	private Key dek;
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
	
	public Encrypter(Key dek) {
		this.dek = dek;
		try {
			cipher = Cipher.getInstance(Decrypter.ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, this.dek);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateKey(Key newKey) {
		this.dek = newKey;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, this.dek);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public byte[] encrypt(String string) {
		byte[] encrypted = null;
		try {
			encrypted = cipher.doFinal(string.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encrypted;
	}

}
