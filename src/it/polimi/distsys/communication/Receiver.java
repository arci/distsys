package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;

import java.util.List;

public interface Receiver {

	public List<Message> receive(Message m);
}
