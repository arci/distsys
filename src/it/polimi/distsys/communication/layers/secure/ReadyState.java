package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.util.List;

public class ReadyState implements ClientState {
	private ClientSecureLayer layer;

	public ReadyState(ClientSecureLayer layer, List<Message> sendingQueue) throws IOException {
		super();
		this.layer = layer;
		for(Message m : sendingQueue){
			layer.send(m);
		}
	}
	
	@Override
	public void keysReceived() {}

	@Override
	public void stop() throws IOException {
		layer.setState(new STOPState(layer));
		layer.sendACK();
	}

	@Override
	public void done() {}

	@Override
	public boolean send(Message msg) {
		return true;
	}

	@Override
	public boolean receive(Message msg) throws IOException {
		return true;
	}
}
