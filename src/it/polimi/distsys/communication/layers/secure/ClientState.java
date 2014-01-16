package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.util.List;

public interface ClientState {
	
	public void keysReceived() throws IOException;
	public void stop() throws IOException;
	public void done();
	public boolean send(Message msg);
	public boolean receive(Message msg) throws IOException;
	public List<Message> getMessages();
}
