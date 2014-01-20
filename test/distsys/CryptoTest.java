package distsys;

import it.polimi.distsys.communication.components.Decrypter;
import it.polimi.distsys.communication.components.FlatTable;
import it.polimi.distsys.communication.layers.secure.ClientSecureLayer;

import java.security.Key;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;

import org.junit.Test;

public class CryptoTest {

    @Test
    public void test() throws Throwable {
	FlatTable table = new FlatTable();
	ClientSecureLayer sec = new ClientSecureLayer();
	UUID id = UUID.randomUUID();

	Key[] oldKeks = table.join(id);
	Key[] newKeks = table.updateKEKs(id);

	Cipher pub = Cipher.getInstance("RSA");
	pub.init(Cipher.ENCRYPT_MODE, sec.getPublic());

	SealedObject[] sos = new SealedObject[FlatTable.BITS];
	for (int i = 0; i < oldKeks.length; i++) {
	    sos[i] = new SealedObject(oldKeks[i], pub);
	}
	sec.init(sos, new SealedObject(table.getDEK(), pub));
	for (int i = 0; i < oldKeks.length; i++) {
	    Cipher kekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
	    kekCipher.init(Cipher.ENCRYPT_MODE, oldKeks[i]);
	    sec.updateKEK(new SealedObject(newKeks[i], kekCipher));
	}

    }
}
