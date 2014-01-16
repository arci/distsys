package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.security.Key;
import java.util.List;

public interface ClientState {
	
	public void keysReceived(List<Key> keks, Key dek);
	public void stop() throws IOException;
	public void done();
	public List<Message> send(Message msg);
	public List<Message> receive(Message msg) throws IOException;
	public List<Message> getMessages();
}
