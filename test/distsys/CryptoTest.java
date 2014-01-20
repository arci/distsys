package distsys;

import static org.junit.Assert.*;
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
		ClientSecureLayer sec1 = new ClientSecureLayer();
		ClientSecureLayer sec2 = new ClientSecureLayer();
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();

		Key[] oldKeks = table.join(id1);
		Key[] newKeks = table.updateKEKs(id1);
		System.out.println("Client 000 joined");

		Cipher pub = Cipher.getInstance("RSA");
		pub.init(Cipher.ENCRYPT_MODE, sec1.getPublic());

		SealedObject[] sos = new SealedObject[FlatTable.BITS];
		for (int i = 0; i < newKeks.length; i++) {
			sos[i] = new SealedObject(newKeks[i], pub);
		}
		sec1.init(sos, new SealedObject(table.getDEK(), pub));
		System.out.println("Client 000 initialized");

		oldKeks = table.join(id2);
		newKeks = table.updateKEKs(id2);
		System.out.println("Client 001 joined");

		System.out.println("updating Client 000");
		for (int i = 0; i < oldKeks.length; i++) {
			Cipher kekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
			kekCipher.init(Cipher.ENCRYPT_MODE, oldKeks[i]);
			sec1.updateKEK(new SealedObject(newKeks[i], kekCipher));
		}
		
		pub.init(Cipher.ENCRYPT_MODE, sec2.getPublic());
		sos = new SealedObject[FlatTable.BITS];
		for (int i = 0; i < newKeks.length; i++) {
			sos[i] = new SealedObject(newKeks[i], pub);
		}
		sec2.init(sos, new SealedObject(table.getDEK(), pub));
		System.out.println("Client 001 initialized");
		
		assertTrue(sec1.getKEK(0).equals(sec2.getKEK(0)));
		assertTrue(sec1.getKEK(1).equals(sec2.getKEK(1)));
		assertFalse(sec1.getKEK(2).equals(sec2.getKEK(2)));
		
		ClientSecureLayer sec3 = new ClientSecureLayer();
		UUID id3 = UUID.randomUUID();

		oldKeks = table.join(id3);
		newKeks = table.updateKEKs(id3);
		System.out.println("Client 010 joined");
		
		System.out.println("updating Client 000");
		for (int i = 0; i < oldKeks.length; i++) {
			Cipher kekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
			kekCipher.init(Cipher.ENCRYPT_MODE, oldKeks[i]);
			sec1.updateKEK(new SealedObject(newKeks[i], kekCipher));
		}
		
		System.out.println("updating Client 001");
		for (int i = 0; i < oldKeks.length; i++) {
			Cipher kekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
			kekCipher.init(Cipher.ENCRYPT_MODE, oldKeks[i]);
			sec2.updateKEK(new SealedObject(newKeks[i], kekCipher));
		}
		
		pub.init(Cipher.ENCRYPT_MODE, sec3.getPublic());
		sos = new SealedObject[FlatTable.BITS];
		for (int i = 0; i < newKeks.length; i++) {
			sos[i] = new SealedObject(newKeks[i], pub);
		}
		sec3.init(sos, new SealedObject(table.getDEK(), pub));
		System.out.println("Client 010 initialized");
		
		assertTrue(sec1.getKEK(0).equals(sec2.getKEK(0)));
		assertTrue(sec2.getKEK(0).equals(sec3.getKEK(0)));
		assertTrue(sec1.getKEK(1).equals(sec2.getKEK(1)));
		assertTrue(sec1.getKEK(2).equals(sec3.getKEK(2)));
		assertFalse(sec1.getKEK(1).equals(sec3.getKEK(1)));
		assertFalse(sec2.getKEK(1).equals(sec3.getKEK(1)));
		assertFalse(sec1.getKEK(2).equals(sec2.getKEK(2)));
		assertFalse(sec2.getKEK(2).equals(sec3.getKEK(2)));
	}
}
