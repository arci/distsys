package it.polimi.distsys.components;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class Decrypter {
	public static final String ALGORITHM = "AES";
    private SecretKey dek;

    public Decrypter(SecretKey dek) {
	this.dek = dek;
    }

    public void updateKey(SecretKey newKey) {
	this.dek = newKey;
    }

    public byte[] decrypt(String string) {
	Cipher cipher;
	byte[] decrypted = null;
	try {
	    cipher = Cipher.getInstance(ALGORITHM);
	    cipher.init(Cipher.DECRYPT_MODE, this.dek);
	    decrypted = cipher.doFinal(string.getBytes());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return decrypted;
    }

}
