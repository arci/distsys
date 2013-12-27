package it.polimi.distsys.peers;

import it.polimi.distsys.communication.Message;

public interface Observer {
	public void update(Message m);

}
