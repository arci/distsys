package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.components.TableException;
import it.polimi.distsys.communication.messages.DONEMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class KeysState implements ServerState {
	private ServerSecureLayer layer;
	private List<UUID> waitingACK = new ArrayList<UUID>();

	public KeysState(ServerSecureLayer layer) {
		super();
		this.layer = layer;
		Iterator<UUID> itr = layer.getTable().iterator();
		while(itr.hasNext()){
			waitingACK.add(itr.next());
		}
	}

	@Override
	public void join(UUID id) {
		//send to the joiner his keks and the dek already generated.
	}

	@Override
	public void leave(UUID id) throws IOException, TableException {
		waitingACK.clear();
		layer.setState(new StoppingState(layer));
		layer.leave(id);
	}

	@Override
	public void ACKReceived(UUID id) throws IOException {
		waitingACK.remove(id);
		if(waitingACK.isEmpty()){
			layer.sendDown(new DONEMessage());
			layer.setState(new NormalState(layer));
		}
	}

}
