package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;

import java.util.List;

public class Stack {
	Layer first;
	Layer last;

	public Stack(Layer first, Layer last) {
		super();
		this.first = first;
		this.last = last;
	}

	public void send(Message msg) {
		first.send(msg);
	}

	public List<Message> receive(List<Message> msgs) {
		return last.receive(msgs);
	}

}
