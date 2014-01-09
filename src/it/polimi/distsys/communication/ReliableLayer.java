package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.NACKMessage;
import it.polimi.distsys.communication.messages.SequenceNumberMessage;
import it.polimi.distsys.communication.messages.StringMessage;
import it.polimi.distsys.components.Printer;
import it.polimi.distsys.components.SequenceNumber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReliableLayer extends Layer {
	private List<Message> sendingQueue;
	private Map<UUID, Integer> lastIDs;
	private int ID;
	private int timeout;
	private UUID uniqueID;
	private boolean sendNACK;

	public ReliableLayer() {
		super();
		ID = 0;
		sendNACK = true;
		lastIDs = new HashMap<UUID, Integer>();
		uniqueID = UUID.randomUUID();
		timeout = (int) (Math.random() * 10);
		sendingQueue = new ArrayList<Message>();
	}

	@Override
	public Message processOnSend(Message msg) {
		ID++;
		SequenceNumber sn = new SequenceNumber(uniqueID, ID);
		SequenceNumberMessage toSend = new SequenceNumberMessage(sn, msg);
		sendingQueue.add(toSend);
		return toSend;
	}

	@Override
	public List<Message> processOnReceive(Message msg) {
		List<Message> toReceive = new ArrayList<Message>();

		Printer.printDebug(getClass(), msg.toString());
		SequenceNumberMessage sn = (SequenceNumberMessage) msg;

		UUID uuid = sn.getSender();
		Integer msgid = sn.getSN();

		if (isMe(uuid)) {
			return toReceive;
		}

		Integer lastID = lastIDs.get(uuid);
		if (lastID == null) {
			lastID = 0;
		}
		Integer offset = msgid - lastID;

		if (offset == 1) {
			// Printer.printDebug(getClass(), "offset == 1");
			lastIDs.put(uuid, msgid);
			toReceive.add(sn.unpack());
		} else if (offset > 1) {
			try {
				Printer.printDebug(getClass(), "sleeping for " + timeout);
				Thread.sleep(timeout * 1000);
				if (sendNACK) {
					Printer.printDebug(getClass(), "Sending NACK");
					sendDown(new NACKMessage(new SequenceNumber(uuid, lastID)));
				}
				sendNACK = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return toReceive;
	}

	public void resend(SequenceNumber sn) throws IOException {
		Printer.printDebug(getClass(), "Resending message with ID " + sn);
		for (Message msg : sendingQueue) {
			SequenceNumberMessage casted = (SequenceNumberMessage) msg;
			if (casted.getSN() > sn.getMessageID()) {
				sendDown(msg);
			}
		}
	}

	public void sendWOffset(int offset, Message msg) throws IOException {
		for (int i = 1; i < offset; i++) {
			Message m = new SequenceNumberMessage(new SequenceNumber(uniqueID,
					ID + i), new StringMessage("Filler message"));
			sendingQueue.add(m);
		}
		ID += offset;
		Message toSend = new SequenceNumberMessage(new SequenceNumber(uniqueID,
				ID), msg);
		sendingQueue.add(toSend);
		sendDown(toSend);
	}

	public boolean isMe(UUID id) {
		return id.equals(uniqueID);
	}

	public void stopNACK() {
		sendNACK = false;
	}

	@Override
	public void join() throws IOException {
		underneath.join();
	}
}
