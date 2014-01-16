package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.components.FlatTable;
import it.polimi.distsys.communication.components.TableException;
import it.polimi.distsys.communication.messages.KeysMessage;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StoppingState implements ServerState {
	private ServerSecureLayer layer;
	private List<UUID> waitingACK = new ArrayList<UUID>();

	public StoppingState(ServerSecureLayer layer) {
		super();
		this.layer = layer;
		Iterator<UUID> itr = layer.getTable().iterator();
		while (itr.hasNext()) {
			waitingACK.add(itr.next());
		}
	}

	@Override
	public void join(UUID id) throws TableException {
		layer.getTable().join(id);
		layer.addJoiner(id);
		waitingACK.add(id);
	}

	@Override
	public void leave(UUID id) throws IOException, TableException {
		layer.getTable().leave(id);
		layer.addLeaver(id);
		ACKReceived(id);
	}

	@Override
	public void ACKReceived(UUID id) throws IOException, TableException {
		waitingACK.remove(id);
		if (waitingACK.isEmpty()) {
			layer.setState(new KeysState(layer));
			// key exchange
			Key dek = layer.getTable().refreshDEK();
			layer.updateDEK(dek);
			List<UUID> joiners = layer.getJoiners();
			List<UUID> leavers = layer.getLeavers();

			for (UUID leaver : leavers) {
				Key[] keks = layer.getTable().updateKEKs(leaver);
				layer.sendDown(new KeysMessage(layer.getTable().getInterested(leaver), layer
						.getTable().getDEK(), keks));
			}
			
			for (UUID joiner : joiners) {
				Map<UUID, List<Integer>> receiver = new HashMap<UUID, List<Integer>>();
				List<Integer> positions = new ArrayList<Integer>();
				for(int i = 0; i< FlatTable.BITS; i++){
					positions.add(i);
				}
				receiver.put(joiner, positions);
				layer.sendDown(new KeysMessage(receiver, layer
						.getTable().getDEK(), layer.getTable().getKEKs(joiner)));
			}
		}
	}

}
