package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.components.TableException;
import it.polimi.distsys.communication.messages.DONEMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SendingDoneState implements ServerState {
	private ServerSecureLayer layer;
	private List<UUID> waitingACK = new ArrayList<UUID>();
	private List<UUID> members = new ArrayList<UUID>();

	public SendingDoneState(ServerSecureLayer layer, List<UUID> members) {
		super();
		this.layer = layer;
		this.members = members;
		waitingACK = new ArrayList<UUID>(members);
	}

	@Override
	public void join(UUID id) {
		//send to the joiner his keks and the dek already generated.
	}

	@Override
	public void leave(UUID id) throws IOException, TableException {
		waitingACK.clear();
		layer.setState(new SendingKeysState(layer, members, new ArrayList<UUID>(), Arrays.asList(id)));
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
