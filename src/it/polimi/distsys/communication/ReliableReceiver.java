package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.SequenceNumberMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReliableReceiver implements Receiver {
	private List<SequenceNumberMessage> queue;
	private List<Message> toReceive;
	private Set<Integer> expected;
	private Integer lastID;

	public ReliableReceiver() {
		queue = new ArrayList<SequenceNumberMessage>();
		toReceive = new ArrayList<Message>();
		expected = new HashSet<Integer>();
		lastID = 0;
	}

	@Override
	public List<Message> receive(Message m) {
//		System.out.println(getClass().getCanonicalName() + ": " + m.toString());
//		SequenceNumberMessage sn = (SequenceNumberMessage) m;
//		Integer ID = sn.getID();
//
//		Integer offset = ID - lastID;
//
//		if (offset == 1) {
//			expected.remove(ID);
//			toReceive.add(sn.unpack());
//			lastID++;
//		} else {
//			for (int i = 1; i < offset; i++) {
//				if (!isInQueue(lastID + i)) {
//					expected.add(lastID + i);
//				}
//			}
//			queue.add(sn);
//		}
//
//		if (expected.contains(ID)) {
//			expected.remove(ID);
//			queue.add(sn);
//		}
//
//		if (expected.isEmpty()) {
//			// ok we can send
//		}
//
//		if (ID != lastID + 1) {
//			expected.add(ID);
//			queue.add(sn);
//		}
//
//		if (ID == lastID + 1) {
//			toReceive.add(sn.unpack());
//		}
//		return toReceive;
		return new ArrayList<Message>(Arrays.asList(m));
	}

	private boolean isInQueue(Integer ID) {
		for (SequenceNumberMessage sn : queue) {
			if (sn.getID() == ID) {
				return true;
			}
		}
		return false;
	}

}
