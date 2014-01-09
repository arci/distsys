package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Layer {
	protected Layer above;
	protected Layer underneath;
	protected boolean sendUp;
	protected boolean sendDown;

	public Layer() {
		sendUp = true;
		sendDown = true;
	}

	public void send(Message msg) throws IOException {
		msg.onSend(this);
		if (sendDown) {
			msg = processOnSend(msg);
			sendDown(msg);
		}
		sendDown = true;
	}

	public List<Message> receive(List<Message> msgs) throws IOException {
		List<Message> toReceive = new ArrayList<Message>();

		for (Message m : msgs) {
			m.onReceive(this);
			if (sendUp) {
				toReceive.addAll(processOnReceive(m));
			}
			sendUp = true;
		}

		return sendUp(toReceive);
	}

	public void stopReceiving() {
		sendUp = false;
	}

	public void stopSending() {
		sendDown = false;
	}
	
	public boolean isSending() {
		return sendDown;
	}
	
	public boolean isReceiving() {
		return sendUp;
	}

	public void setAbove(Layer above) {
		this.above = above;
	}

	public void setUnderneath(Layer underneath) {
		this.underneath = underneath;
	}

	public List<Message> sendUp(List<Message> msgs) throws IOException {
		try {
			return above.receive(msgs);
		} catch (NullPointerException e) {
			return msgs;
		}
	}

	public void sendDown(Message msg) throws IOException {
		underneath.send(msg);
	}

	public abstract List<Message> processOnReceive(Message msg);

	public abstract Message processOnSend(Message msg);
	
	public abstract void join() throws IOException;

}
