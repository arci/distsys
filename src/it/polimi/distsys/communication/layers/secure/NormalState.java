package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.components.TableException;
import it.polimi.distsys.communication.messages.STOPMessage;

import java.io.IOException;
import java.util.UUID;

public class NormalState implements ServerState {
	private ServerSecureLayer layer;

	public NormalState(ServerSecureLayer layer) {
		super();
		this.layer = layer;
	}

	@Override
	public void join(UUID id) throws IOException, TableException {
		layer.getTable().join(id);
		layer.getTable().refreshDEK();
		layer.addJoiner(id);
		layer.sendDown(new STOPMessage());
		layer.setState(new StoppingState(layer));
	}

	@Override
	public void leave(UUID id) throws TableException, IOException {
		layer.getTable().leave(id);
		layer.getTable().refreshDEK();
		layer.addLeaver(id);
		layer.sendDown(new STOPMessage());
		layer.setState(new StoppingState(layer));
	}

	@Override
	public void ACKReceived(UUID id) {}

}
