package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.NACKMessage;
import it.polimi.distsys.communication.messages.SequenceNumberMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReliableLayer implements Layer {
	private Map<Integer, Message> receivingQueue;
	private Integer lastID;
	private int ID;
	private Layer above;
	private Layer underneath;

	public ReliableLayer() {
		super();
		ID = 0;
		lastID = 0;
		receivingQueue = new HashMap<Integer, Message>();
	}

	@Override
	public List<Message> receive(List<Message> msgs) {
		List<Message> toReceive = new ArrayList<Message>();

		for (Message m : msgs) {
			System.out.println(getClass().getCanonicalName() + ": "
					+ m.toString());
			SequenceNumberMessage sn = (SequenceNumberMessage) m;
			Integer ID = sn.getID();

			Integer offset = ID - lastID;

			receivingQueue.put(ID, sn.unpack());

			for (int i = 1; i < offset; i++) {
				if (!receivingQueue.keySet().contains(lastID + i)) {
					receivingQueue.put(lastID + i, null);
					send(new NACKMessage(lastID + i));
				}
			}

			List<Integer> sorted = new ArrayList<Integer>(receivingQueue.keySet());
			Collections.sort(sorted);

			for (Integer i : sorted) {
				Message element = receivingQueue.get(i);
				if (element == null) {
					break;
				} else {
					receivingQueue.remove(i);
					lastID = i;
					toReceive.add(element);
				}
			}
		}

		return toReceive;
	}

	@Override
	public Message send(Message msg) {
		ID++;
		Message toSend = new SequenceNumberMessage(ID, msg);
		return toSend;
	}
	
	public void setAbove(Layer above) {
		this.above = above;
	}
	
	public void setUnderneath(Layer underneath) {
		this.underneath = underneath;
	}
}
