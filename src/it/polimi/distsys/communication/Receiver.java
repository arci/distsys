package it.polimi.distsys.communication;

import java.util.List;

public interface Receiver {

	public List<Message> receive(Message m);
}
