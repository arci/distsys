package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class STOPState implements ClientState {
	private ClientSecureLayer layer;
	private List<Message> sendingQueue;

	public STOPState(ClientSecureLayer layer) {
		super();
		this.layer = layer;
		sendingQueue = new ArrayList<Message>();
	}
	
	@Override
	public void keysReceived() throws IOException {
		layer.sendACK();
	}

	@Override
	public void stop() {}

	@Override
	public void done() {
		layer.setState(new OKState(layer));
	}

	@Override
	public boolean send(Message msg) {
		sendingQueue.add(msg);
		return false;
	}

	@Override
	public boolean receive(Message msg) throws IOException {
		return true;
	}

	public List<Message> getMessages() {
		List<Message> cloned = new ArrayList<Message>(sendingQueue);
		sendingQueue.clear();
		return cloned;
	}
}
