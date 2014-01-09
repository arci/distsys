package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.util.List;

public class Stack {
	Layer first;
	Layer last;

	public Stack(Layer first, Layer last) {
		super();
		this.first = first;
		this.last = last;
	}

	public void send(Message msg) throws IOException {
		first.send(msg);
	}

	public List<Message> receive(List<Message> msgs) throws IOException {
		return last.receive(msgs);
	}
	
	public void join() throws IOException{
		first.join();
	}

}
