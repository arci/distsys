package it.polimi.distsys.communication.secure;


import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DONEState implements ServerState {
	private ServerSecureLayer layer;
	private List<UUID> waitingACK = new ArrayList<UUID>();

	public DONEState(ServerSecureLayer layer, List<UUID> members) {
		super();
		this.layer = layer;
		waitingACK = new ArrayList<UUID>(members);
	}

	@Override
	public void join(UUID id, Key publicKey) throws IOException, TableException {
		waitingACK.clear();
		layer.setState(new NormalState(layer));
		layer.join(id, publicKey);
	}

	@Override
	public void leave(UUID id) throws IOException, TableException {
		waitingACK.clear();
		layer.setState(new NormalState(layer));
		layer.leave(id);
	}

	@Override
	public void ACKReceived(UUID id) throws IOException {
		waitingACK.remove(id);
		if (waitingACK.isEmpty()) {
			layer.setState(new NormalState(layer));
		}
	}

}
