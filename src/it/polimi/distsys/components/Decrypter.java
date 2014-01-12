package it.polimi.distsys.components;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Decrypter {
	public static final String ALGORITHM = "DES";
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

	public String decrypt(String string) {
		ByteArrayInputStream bs = new ByteArrayInputStream(string.getBytes());
		CipherInputStream cs = new CipherInputStream(bs, cipher);
		byte[] decrypted = new byte[5000];
		try {
			cs.read(decrypted);
			cs.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return new String(decrypted);
	}

}
