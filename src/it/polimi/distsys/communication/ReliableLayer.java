package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.NACKMessage;
import it.polimi.distsys.communication.messages.SequenceNumberMessage;
import it.polimi.distsys.communication.messages.StringMessage;
import it.polimi.distsys.components.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReliableLayer extends Layer {
	private Map<Integer, Message> receivingQueue;
	private Map<Integer, Message> sendingQueue;
	private Integer lastID;
	private int ID;
	private int timeout;

	public ReliableLayer() {
		super();
		ID = 0;
		lastID = 0;
		timeout = (int) (Math.random() * 10);
		receivingQueue = new HashMap<Integer, Message>();
		sendingQueue = new HashMap<Integer, Message>();
	}

	@Override
	public Message processOnSend(Message msg) {
		ID++;
		Message toSend = new SequenceNumberMessage(ID, msg);
		sendingQueue.put(ID, msg);
		return toSend;
	}

	@Override
	public List<Message> processOnReceive(Message msg) {
		List<Message> toReceive = new ArrayList<Message>();

		Printer.printDebug(getClass(), msg.toString());
		SequenceNumberMessage sn = (SequenceNumberMessage) msg;
		Integer ID = sn.getID();

		Integer offset = ID - lastID;

		receivingQueue.put(ID, sn.unpack());

		for (int i = 1; i < offset; i++) {
			if (!receivingQueue.keySet().contains(lastID + i)) {
				receivingQueue.put(lastID + i, null);
				Printer.printDebug(getClass(), "Adding null message " + (lastID + i));
				try {
					Printer.printDebug(getClass(), "Sleeping for " + timeout);
					Thread.sleep(timeout * 1000);
					if (isSending()) {
						sendDown(new NACKMessage(lastID + i));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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

		return toReceive;

	}

	public void resend(Integer messageID) throws IOException {
		Printer.printDebug(getClass(), "Resending message with ID " + messageID);
		sendDown(new SequenceNumberMessage(messageID,
				sendingQueue.get(messageID)));
	}

	public void sendWOffset(int offset, Message msg) throws IOException {
		for (int i = 1; i < offset; i++) {
			sendingQueue.put(ID + i, new StringMessage("Filler message"));
		}
		ID += offset;
		Message toSend = new SequenceNumberMessage(ID, msg);
		sendingQueue.put(ID, msg);
		sendDown(toSend);
	}

	@Override
	public void join() throws IOException {
		underneath.join();
	}
}
