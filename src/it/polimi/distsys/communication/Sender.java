package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;


public interface Sender {

	public void send(Message msg);
}
