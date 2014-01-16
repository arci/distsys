package it.polimi.distsys.communication.layers.secure;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class StoppingState implements ServerState {
	private ServerSecureLayer layer;
	private List<UUID> waitingACK;

	public StoppingState(ServerSecureLayer layer) {
		super();
		this.layer = layer;
		waitingACK = new ArrayList<UUID>();
		Iterator<UUID> itr = layer.getTable().iterator();
		while(itr.hasNext()){
			waitingACK.add(itr.next());
		}
	}

	@Override
	public void join(UUID id) {
		waitingACK.add(id);
	}

	@Override
	public void leave(UUID id) {
		waitingACK.add(id);
	}

	@Override
	public void ACKReceived(UUID id) {
		waitingACK.remove(id);
		if(waitingACK.isEmpty()){
			layer.setState(layer.getKeysState());
			//key exchange
		}
	}

}
