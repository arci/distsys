package it.polimi.distsys.peers;

import it.polimi.distsys.communication.messages.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageQueue {
	private List<Message> messages;

	public MessageQueue() {
		messages = new ArrayList<Message>();
	}
	
	public synchronized List<Message> getMessages() {
		while (messages.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<Message> cloned = new ArrayList<Message>(messages);
		messages.clear();
		return cloned;
	}

	public synchronized void addMessages(List<Message> msgs) {
		messages.addAll(msgs);
		notifyAll();
	}

}
