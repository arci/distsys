package it.polimi.distsys.communication;

import it.polimi.distsys.chat.Printer;
import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Layer {
	protected Layer above;
	protected Layer underneath;
	protected boolean sendUp = true;
	protected boolean sendDown = true;

	public void send(Message msg) throws IOException {
		Printer.printDebug(getClass(), "sending "
				+ msg.getClass().getSimpleName());
		
		List<Message> toSend = new ArrayList<Message>();
		msg.onSend(this);
		synchronized (this) {
			if (sendDown) {
				toSend = processOnSend(msg);
				for(Message m : toSend){
					sendDown(m);
				}
			}
			sendDown = true;	
		}
	}

	public List<Message> receive(List<Message> msgs) throws IOException {
		List<Message> toReceive = new ArrayList<Message>();

		for (Message m : msgs) {
			Printer.printDebug(getClass(), "receiving "
					+ m.getClass().getSimpleName() + "-> " + m.toString());
			m.onReceive(this);
			synchronized (this) {
				if (sendUp) {
					toReceive.addAll(processOnReceive(m));
				}
				sendUp = true;
			}
		}

		return sendUp(toReceive);
	}

	public synchronized void stopReceiving() {
		sendUp = false;
	}

	public synchronized void stopSending() {
		sendDown = false;
	}

	public synchronized boolean isSending() {
		return sendDown;
	}

	public synchronized boolean isReceiving() {
		return sendUp;
	}

	public void setAbove(Layer above) {
		this.above = above;
	}

	public void setUnderneath(Layer underneath) {
		this.underneath = underneath;
	}

	public List<Message> sendUp(List<Message> msgs) throws IOException {
		if (above == null) {
			return msgs;
		}
		return above.receive(msgs);
	}

	public void sendDown(Message msg) throws IOException {
		underneath.send(msg);
	}

	public abstract List<Message> processOnReceive(Message msg)
			throws IOException;

	public abstract List<Message> processOnSend(Message msg);

	public abstract void join() throws IOException;
	public abstract void leave() throws IOException;

}
