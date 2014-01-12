package it.polimi.distsys.components;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class Decrypter {

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
	    cipher = Cipher.getInstance("DES");
	    cipher.init(Cipher.DECRYPT_MODE, this.dek);
	    decrypted = cipher.doFinal(string.getBytes());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return decrypted;
    }

}
