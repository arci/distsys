package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
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
	public void keysReceived(List<Key> keks, Key dek) {
		layer.updateKEKs(keks);
		layer.updateDEK(dek);
	}

	@Override
	public void stop() {}

	@Override
	public void done() {
		layer.setState(layer.getOkState());
	}

	@Override
	public List<Message> send(Message msg) {
		sendingQueue.add(msg);
		return new ArrayList<Message>();
	}

	@Override
	public List<Message> receive(Message msg) throws IOException {
		return new ArrayList<Message>(Arrays.asList(msg));
	}

	public List<Message> getMessages() {
		return sendingQueue;
	}
}
