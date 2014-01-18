package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.components.FlatTable;
import it.polimi.distsys.communication.components.TableException;
import it.polimi.distsys.communication.messages.KeysMessage;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SendingKeysState implements ServerState {
	private ServerSecureLayer layer;
	private List<UUID> waitingACK = new ArrayList<UUID>();
	private List<UUID> members = new ArrayList<UUID>();
	private List<UUID> joiners = new ArrayList<UUID>();
	private List<UUID> leavers = new ArrayList<UUID>();

	public SendingKeysState(ServerSecureLayer layer, List<UUID> members,
			List<UUID> joiners, List<UUID> leavers) {
		super();
		this.layer = layer;
		this.members = members;
		this.joiners = joiners;
		this.leavers = leavers;
		waitingACK = new ArrayList<UUID>(members);
	}

	@Override
	public void join(UUID id) throws TableException {
		joiners.add(id);
		waitingACK.add(id);
	}

	@Override
	public void leave(UUID id) throws IOException, TableException {
		leavers.add(id);
		ACKReceived(id);
	}

	@Override
	public void ACKReceived(UUID id) throws IOException, TableException {
		waitingACK.remove(id);
		if (waitingACK.isEmpty()) {
			// key exchange
			FlatTable table = layer.getTable();
			Key dek = table.refreshDEK();
			layer.updateDEK(dek);

			for (UUID leaver : leavers) {
				Key[] keks = table.updateKEKs(leaver);
				Map<UUID, List<Integer>> interested = table.getInterested(leaver);
				table.leave(leaver);
				layer.sendDown(new KeysMessage(interested,
						dek, keks));
			}

			for (UUID joiner : joiners) {
				table.join(joiner);
				Map<UUID, List<Integer>> receiver = new HashMap<UUID, List<Integer>>();
				List<Integer> positions = new ArrayList<Integer>();
				for (int i = 0; i < FlatTable.BITS; i++) {
					positions.add(i);
				}
				receiver.put(joiner, positions);
				layer.sendDown(new KeysMessage(receiver, dek, table
						.getKEKs(joiner)));
			}

			layer.setState(new SendingDoneState(layer, members));
		}
	}

}
