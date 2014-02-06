package distsys;

import static org.junit.Assert.assertTrue;
import it.polimi.distsys.communication.secure.ClientSecureLayer;
import it.polimi.distsys.communication.secure.Decrypter;
import it.polimi.distsys.communication.secure.FlatTable;

import java.security.Key;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;

import org.junit.Test;

public class CryptoTest {

	// TODO redo

	@Test
	public void test() throws Throwable {

		/* INIT */

		FlatTable table = new FlatTable();
		ClientSecureLayer sl000 = new ClientSecureLayer();
		ClientSecureLayer sl001 = new ClientSecureLayer();
		ClientSecureLayer sl010 = new ClientSecureLayer();

		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		UUID id3 = UUID.randomUUID();

		SealedObject[] sos = new SealedObject[FlatTable.BITS];

		/* INIT 000 AND JOIN */

		Cipher pub000 = Cipher.getInstance("RSA");
		pub000.init(Cipher.ENCRYPT_MODE, sl000.getPublic());

		Key[] oldKeks = table.join(id1);
		Key[] newKeks = table.updateKEKs(id1);
		System.out.println("Client 000 joined");

		for (int i = 0; i < newKeks.length; i++) {
			sos[i] = new SealedObject(newKeks[i], pub000);
		}

		sl000.init(sos, new SealedObject(table.getDEK(), pub000));
		System.out.println("Client 000 initialized");

		/* JOIN 001 */

		oldKeks = table.join(id2);
		newKeks = table.updateKEKs(id2);
		Key oldDek = table.getDEK();
		Key newDek = table.refreshDEK();

		System.out.println("Client 001 joined");

		/* INIT 001 */

		Cipher pub001 = Cipher.getInstance("RSA");
		pub001.init(Cipher.ENCRYPT_MODE, sl001.getPublic());

		for (int i = 0; i < newKeks.length; i++) {
			sos[i] = new SealedObject(newKeks[i], pub001);
		}

		sl001.init(sos, new SealedObject(newDek, pub001));
		System.out.println("Client 001 initialized");

		/* UPDATING */
		/* new dek with old dek, each key keks with its correspondi old kek */

		System.out.println("updating Client 000");

		for (int i = 0; i < newKeks.length; i++) {
			Cipher kekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
			kekCipher.init(Cipher.ENCRYPT_MODE, oldKeks[i]);
			sos[i] = new SealedObject(newKeks[i], kekCipher);

		}

		Cipher dekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
		dekCipher.init(Cipher.ENCRYPT_MODE, oldDek);

		sl000.updateOnJoin(sos, new SealedObject(newDek, dekCipher));
		System.out.println("Client 000 updated");

		/* assertions */

		assertTrue(sl000.getKEK(0).equals(sl001.getKEK(0)));
		assertTrue(sl000.getKEK(1).equals(sl001.getKEK(1)));
		assertTrue(!sl000.getKEK(2).equals(sl001.getKEK(2)));

		/* JOIN 010 */

		oldKeks = table.join(id3);
		newKeks = table.updateKEKs(id3);
		oldDek = table.getDEK();

		newDek = table.refreshDEK();

		System.out.println("Client 010 joined");

		/* INIT 010 */

		Cipher pub010 = Cipher.getInstance("RSA");
		pub010.init(Cipher.ENCRYPT_MODE, sl010.getPublic());

		for (int i = 0; i < newKeks.length; i++) {
			sos[i] = new SealedObject(newKeks[i], pub010);
		}

		sl010.init(sos, new SealedObject(newDek, pub010));
		System.out.println("Client 010 initialized");

		/* UPDATING */
		/* new dek with old dek, each key keks with its corresponding old kek */

		System.out.println("updating Client 000");

		for (int i = 0; i < newKeks.length; i++) {
			Cipher kekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
			kekCipher.init(Cipher.ENCRYPT_MODE, oldKeks[i]);
			sos[i] = new SealedObject(newKeks[i], kekCipher);

		}

		Cipher dekCipher1 = Cipher.getInstance(Decrypter.ALGORITHM);
		dekCipher1.init(Cipher.ENCRYPT_MODE, oldDek);

		sl000.updateOnJoin(sos, new SealedObject(newDek, dekCipher1));
		System.out.println("Client 000 updated");
		System.out.println(" updating Client 001");

		sl001.updateOnJoin(sos, new SealedObject(newDek, dekCipher1));
		System.out.println("Client 001 updated");

		/* assertions */

		assertTrue(sl000.getKEK(0).equals(sl001.getKEK(0)));
		assertTrue(sl000.getKEK(1).equals(sl001.getKEK(1)));
		assertTrue(!sl000.getKEK(2).equals(sl001.getKEK(2)));

		assertTrue(sl000.getKEK(0).equals(sl010.getKEK(0)));
		assertTrue(!sl000.getKEK(1).equals(sl010.getKEK(1)));
		assertTrue(sl000.getKEK(2).equals(sl010.getKEK(2)));

		assertTrue(sl001.getKEK(0).equals(sl010.getKEK(0)));
		assertTrue(!sl001.getKEK(1).equals(sl010.getKEK(1)));
		assertTrue(!sl001.getKEK(2).equals(sl010.getKEK(2)));

		/* LEAVE */

		/* LEAVE 001 */
		oldKeks = table.getKEKs(id2);
		Key[] oldOtherKeks = table.getOtherKEKs(id2);
		newKeks = table.updateKEKs(id2);

		oldDek = table.getDEK();
		newDek = table.refreshDEK();

		table.leave(id2);

		SealedObject[] keks = new SealedObject[FlatTable.BITS];
		SealedObject[] deks = new SealedObject[FlatTable.BITS];

		System.out.println("Client 001 leaved");
		/* UPDATING */
		/*
		 * new dek with old keks, each key keks with its correspondi old kek and
		 * the with oldDek
		 */

		System.out.println("updating Client 000");

		Cipher oldDekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
		oldDekCipher.init(Cipher.ENCRYPT_MODE, oldDek);
		Cipher kekOtherCipher = Cipher.getInstance(Decrypter.ALGORITHM);

		Cipher kekOldCipher = Cipher.getInstance(Decrypter.ALGORITHM);

		for (int i = 0; i < oldOtherKeks.length; i++) {
			kekOtherCipher.init(Cipher.ENCRYPT_MODE, oldOtherKeks[i]);
			deks[i] = new SealedObject(newDek, kekOtherCipher);
		}

		for (int i = 0; i < oldKeks.length; i++) {
			kekOldCipher.init(Cipher.ENCRYPT_MODE, oldKeks[i]);

			keks[i] = new SealedObject(new SealedObject(newKeks[i],
					kekOldCipher), oldDekCipher);

		}

		sl000.updateOnLeave(keks, deks);
		System.out.println("Client 000 updated");
		System.out.println("updating Client 010");
		sl010.updateOnLeave(keks, deks);
		System.out.println("Client 010 updated");

		/* assertions */

		assertTrue(sl010.getKEK(0).equals(sl000.getKEK(0)));
		assertTrue(!sl010.getKEK(1).equals(sl000.getKEK(1)));
		assertTrue(sl010.getKEK(2).equals(sl000.getKEK(2)));

		assertTrue(!sl000.getKEK(0).equals(sl001.getKEK(0)));
		assertTrue(!sl000.getKEK(1).equals(sl001.getKEK(1)));
		assertTrue(!sl000.getKEK(2).equals(sl001.getKEK(2)));

		assertTrue(!sl010.getKEK(0).equals(sl001.getKEK(0)));
		assertTrue(!sl010.getKEK(1).equals(sl001.getKEK(1)));
		assertTrue(!sl010.getKEK(2).equals(sl001.getKEK(2)));
	}
}
