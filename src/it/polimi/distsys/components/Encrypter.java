package it.polimi.distsys.components;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

public class Encrypter {

	private Key dek;
	private Cipher cipher;

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

	public String encrypt(String string) {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		CipherOutputStream cs = new CipherOutputStream(bs, cipher);
		try {
			cs.write(string.getBytes());
			cs.flush();
			cs.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] encrypted = bs.toByteArray();
		return new String(encrypted);
	}

}
