package distsys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import it.polimi.distsys.communication.components.FlatTable;
import it.polimi.distsys.communication.components.TableException;

import java.security.Key;
import java.util.UUID;

import org.junit.Test;

public class FlatTableTest {

    @Test
    public void allOK() throws TableException {

	FlatTable table = new FlatTable();

	UUID joiner = UUID.randomUUID();

	table.join(joiner);

	Key[] oldKeks = table.getKEKs(joiner);
	Key[] oldOther = table.getOtherKEKs(joiner);

	Key[] newKeks = table.updateKEKs(joiner);

	for (int i = 0; i < oldKeks.length; i++) {
	    assertFalse(oldKeks[i].equals(newKeks[i]));
	}

	Key[] newOther = table.getOtherKEKs(joiner);
	for (int i = 0; i < oldKeks.length; i++) {
	    assertEquals(oldOther[i], newOther[i]);
	}

    }

    @Test
    public void getRight() throws TableException {

	FlatTable table = new FlatTable();

	UUID joiner000 = UUID.randomUUID();
	UUID joiner001 = UUID.randomUUID();
	UUID joiner010 = UUID.randomUUID();

	table.join(joiner000);
	table.join(joiner001);
	table.join(joiner010);

	Key[] keks000 = table.getKEKs(joiner000);
	Key[] keks001 = table.getKEKs(joiner001);
	Key[] keks010 = table.getKEKs(joiner010);

	assertEquals(keks000[0], keks001[0]);
	assertEquals(keks000[0], keks010[0]);
	assertEquals(keks001[0], keks010[0]);

	assertEquals(keks000[1], keks001[1]);
	assertFalse(keks010[1].equals(keks000[1]));
	assertFalse(keks010[1].equals(keks001[1]));

	assertEquals(keks000[2], keks010[2]);
	assertFalse(keks001[2].equals(keks000[2]));
	assertFalse(keks001[2].equals(keks010[2]));
    }

}
