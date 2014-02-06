package distsys;

import static org.junit.Assert.assertEquals;
import it.polimi.distsys.communication.causal.VectorClock;

import java.util.UUID;

import org.junit.Test;

public class VectorClockTest {

	@Test
	public void test() {
		UUID client1 = UUID.randomUUID();
		UUID client2 = UUID.randomUUID();
		UUID client3 = UUID.randomUUID();
		VectorClock vc1 = new VectorClock(client1);
		VectorClock vc2 = new VectorClock(client2);
		VectorClock vc3 = new VectorClock(client3);

		// client 1 send and everybody receive (so everybody knows everybody)
		vc1.increment();
		vc3.merge(vc1);
		vc2.merge(vc1);

		// client1 send
		vc1.increment();
		// client2 receive and accept
		assertEquals(vc1.compareTo(vc2), 0);
		vc2.merge(vc1);
		// client2 reply
		vc2.increment();
		// client3 receive out of order
		System.out.println(vc3.compareTo(vc2));
		assertEquals(vc3.compareTo(vc2), -1);
		// client3 receive from vc1 and accept
		assertEquals(vc3.compareTo(vc1), 0);
		vc3.merge(vc1);
		// client1 receive and accept
		assertEquals(vc1.compareTo(vc2), 0);
		vc1.merge(vc2);
	}
}
