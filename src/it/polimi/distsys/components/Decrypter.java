package it.polimi.distsys.components;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Decrypter {
	public static final String ALGORITHM = "AES";
	private Key dek;
	private Cipher cipher;

	public Decrypter(Key dek) {
		this.dek = dek;
		try {
			this.cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, this.dek);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateKey(SecretKey newKey) {
		this.dek = newKey;
		try {
			cipher.init(Cipher.DECRYPT_MODE, this.dek);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String decrypt(byte[] buffer) {
		byte[] decrypted = null;
		try {
			decrypted = cipher.doFinal(buffer);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(decrypted);
	}

}
