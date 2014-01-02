package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.NACKMessage;
import it.polimi.distsys.communication.messages.SequenceNumberMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReliableReceiver implements Receiver {
	private Map<Integer, Message> queue;
	private Integer lastID;

	public ReliableReceiver() {
		queue = new HashMap<Integer, Message>();
		lastID = 0;
	}

	@Override
	public List<Message> receive(Message m) {
		System.out.println(getClass().getCanonicalName() + ": " + m.toString());
		List<Message> toReceive = new ArrayList<Message>();
		SequenceNumberMessage sn = (SequenceNumberMessage) m;
		Integer ID = sn.getID();

		Integer offset = ID - lastID;

		queue.put(ID, sn.unpack());

		for (int i = 1; i < offset; i++) {
			if (!queue.keySet().contains(lastID + i)) {
				queue.put(lastID + i, null);
				toReceive.add(new NACKMessage(lastID + i));
			}
		}

		List<Integer> sorted = new ArrayList<Integer>(queue.keySet());
		Collections.sort(sorted);

		for (Integer i : sorted) {
			Message element = queue.get(i);
			if (element == null) {
				break;
			} else {
				queue.remove(i);
				lastID = i;
				toReceive.add(element);
			}
		}

		return toReceive;
	}

}
