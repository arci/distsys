package it.polimi.distsys.communication.reliable;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.chat.Printer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.NACKMessage;
import it.polimi.distsys.communication.messages.SequenceNumberMessage;
import it.polimi.distsys.communication.messages.StringMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReliableLayer extends Layer {
	public static boolean DEBUG;
	private List<Message> sendingQueue;
	private Map<UUID, Integer> lastIDs;
	private int ID = 0;
	private final static int NUMBER_MESSAGES_TO_CLEAR = 50;
	private final static int CLEAR_INTERVAL = NUMBER_MESSAGES_TO_CLEAR * 2;
	private final UUID uniqueID = Peer.ID;
	private NACKer nacker;

	public ReliableLayer() {
		super();
		lastIDs = new HashMap<UUID, Integer>();
		sendingQueue = new ArrayList<Message>();
		nacker = new NACKer(this);
	}

	@Override
	public List<Message> processOnSend(Message msg) {
		ID++;
		SequenceNumber sn = new SequenceNumber(uniqueID, ID);
		SequenceNumberMessage toSend = new SequenceNumberMessage(sn, msg);
		sendingQueue.add(toSend);
		if((ID + 1) % CLEAR_INTERVAL == 0){
			sendingQueue.subList(0, NUMBER_MESSAGES_TO_CLEAR).clear();
		}
		return new ArrayList<Message>(Arrays.asList(toSend));
	}

	@Override
	public List<Message> processOnReceive(Message msg) throws IOException {
		List<Message> toReceive = new ArrayList<Message>();
		SequenceNumberMessage sn = (SequenceNumberMessage) msg;

		UUID uuid = sn.getSn().getClientID();
		Integer msgid = sn.getSn().getMessageID();

		if (isMe(uuid)) {
			Printer.printDebug(getClass(), "sent by myself... don't care");
			return toReceive;
		}

		Integer lastID = lastIDs.get(uuid);
		if (lastID == null) {
			// if i'm new to that client, I suppose this is his first message to
			// me. So i force its receiving.
			lastID = msgid - 1;
		}
		Integer offset = msgid - lastID;

		if (offset == 1) {
			// Printer.printDebug(getClass(), "offset == 1");
			lastIDs.put(uuid, msgid);
			toReceive.add(sn.unpack());
			// if(msgid % ACK_INTERVAL == 0){
			// SequenceNumber toProcess = new SequenceNumber(uuid, msgid);
			// sendDown(new ACKMessage(toProcess));
			// }
		} else if (offset > 1) {
			SequenceNumber toProcess = new SequenceNumber(uuid, lastID);
			nacker.processNACK(toProcess);
		}

		return toReceive;
	}

	public void resend(SequenceNumber sn) throws IOException {
		Printer.printDebug(getClass(), "Resending messages from ID " + sn);
		for (Message msg : sendingQueue) {
			SequenceNumberMessage casted = (SequenceNumberMessage) msg;
			if (casted.getSn().getMessageID() > sn.getMessageID()) {
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

	public void stopNACK(UUID id) {
		nacker.stopNACK(id);
	}

	public void cleanQueue(int messageID) {}

	@Override
	public void join() throws IOException {
		underneath.join();
	}

	// NACKer and Processer classes handle all the timeout part.
	// Why separate classes and Threads?
	// I cannot let the main receiving thread sleep, if so,
	// who will receive messages and process NACKs?!
	// So:
	// 1. let's create a thread pool
	// 2. in order to have one thread processing NACK for each member of the
	// group
	// 3. if a NACK has to be sent (and there is not a thread already processing
	// another NACK for that process) let's create a thread
	// 4. the thread will sleep for a random timeout
	// 5. eventually it will send a NACK, only if a NACK message hasn't stopped
	// it
	//
	// Points from 1 to 3 are performed by the NACKer, points 4 and 5 by the
	// Processer.
	private class NACKer {
		private ExecutorService executor;
		private Map<UUID, Processer> processing;
		private ReliableLayer layer;

		public NACKer(ReliableLayer layer) {
			executor = Executors.newCachedThreadPool();
			processing = new HashMap<UUID, Processer>();
			this.layer = layer;
		}

		public void stopNACK(UUID id) {
			try {
				processing.get(id).stopNACK();
			} catch (NullPointerException e) {
				Printer.printDebug(getClass(), "process not active on id " + id);
			}
		}

		public void processNACK(SequenceNumber sn) {
			UUID id = sn.getClientID();
			if (!processing.keySet().contains(id)) {
				Processer proc = new Processer(sn);
				processing.put(id, proc);
				executor.execute(proc);
			}
		}

		private class Processer implements Runnable {
			private SequenceNumber sn;
			private boolean sendNACK = true;

			public Processer(SequenceNumber sn) {
				this.sn = sn;
			}

			@Override
			public void run() {
				int timeout = (int) (Math.random() * 10);
				try {
					Printer.printDebug(getClass(), "UUID: " + sn.getClientID()
							+ " sleeping for " + timeout);
					Thread.sleep(timeout * 1000);
					if (isNACKtoSend()) {
						layer.sendDown(new NACKMessage(sn));
					}
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}

				processing.remove(sn.getClientID());
			}

			public synchronized void stopNACK() {
				Printer.printDebug(getClass(), "NACK stopped");
				sendNACK = false;
			}

			private synchronized boolean isNACKtoSend() {
				return sendNACK;
			}
		}

	}

	@Override
	public void leave() throws IOException {
		underneath.leave();
	}
}
