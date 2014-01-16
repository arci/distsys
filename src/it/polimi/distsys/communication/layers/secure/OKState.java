package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OKState implements ClientState {
	private ClientSecureLayer layer;

	public OKState(ClientSecureLayer layer) {
		super();
		this.layer = layer;
	}
	
	@Override
	public void keysReceived(List<Key> keks, Key dek) {}

	@Override
	public void stop() throws IOException {
		layer.setState(layer.getStopState());
		layer.sendACK();
	}

	@Override
	public void done() {}

	@Override
	public List<Message> send(Message msg) {
		return new ArrayList<Message>(Arrays.asList(msg));
	}

	@Override
	public List<Message> receive(Message msg) throws IOException {
		return new ArrayList<Message>(Arrays.asList(msg));
	}

	@Override
	public List<Message> getMessages() {
		return null;
	}

}
