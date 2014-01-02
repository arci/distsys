package it.polimi.distsys.communication;

import java.util.List;

import it.polimi.distsys.communication.messages.Message;

public interface Layer {

	public Message send(Message msg);

	public List<Message> receive(List<Message> msgs);

}
