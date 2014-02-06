package it.polimi.distsys.communication.secure;

import it.polimi.distsys.Printer;
import it.polimi.distsys.communication.messages.DONEMessage;
import it.polimi.distsys.communication.messages.InitMessage;
import it.polimi.distsys.communication.messages.KeysJoinMessage;
import it.polimi.distsys.communication.messages.KeysLeaveMessage;
import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KeysState implements ServerState {
	private ServerSecureLayer layer;
	private List<UUID> waitingACK = new ArrayList<UUID>();
	private List<UUID> members = new ArrayList<UUID>();
	private List<UUID> joiners = new ArrayList<UUID>();
	private List<UUID> leavers = new ArrayList<UUID>();

	public KeysState(ServerSecureLayer layer, List<UUID> members,
			List<UUID> joiners, List<UUID> leavers) {
		super();
		this.layer = layer;
		this.members = members;
		this.joiners = joiners;
		this.leavers = leavers;
		waitingACK = new ArrayList<UUID>(members);
	}

	@Override
	public void join(UUID id, Key publicKey) throws TableException {
		joiners.add(id);
		layer.getTable().addPublicKey(id, publicKey);
	}

	@Override
	public void leave(UUID id) throws IOException, TableException {
		leavers.add(id);
		layer.getTable().removePublicKey(id);
		ACKReceived(id);
	}

	@Override
	public void ACKReceived(UUID id) throws IOException, TableException {
		waitingACK.remove(id);
		if (waitingACK.isEmpty()) {
			// key exchange
			FlatTable table = layer.getTable();
			List<Message> toSend = new ArrayList<Message>();

			try {

				for (UUID leaver : leavers) {
					Printer.printDebug(getClass(), "processing leaver " + leaver.toString().substring(0, 4));
					Key oldDek = table.getDEK();
					Key newDek = table.refreshDEK();
					layer.updateDEK(newDek);
					Key[] oldKeks = table.getKEKs(leaver);
					Key[] newKeks = table.updateKEKs(leaver);
					Key[] remainingKeks = table.getOtherKEKs(leaver);
					table.leave(leaver);
					toSend.add(new KeysLeaveMessage(newKeks, newDek, oldKeks, remainingKeks, oldDek));
				}

				for (UUID joiner : joiners) {
					Printer.printDebug(getClass(), "processing joiner " + joiner.toString().substring(0, 4));
					Key oldDek = table.getDEK();
					Key newDek = table.refreshDEK();
					layer.updateDEK(newDek);
					Key[] oldKeks = table.join(joiner);
					Key[] newKeks = table.updateKEKs(joiner);
					// encrypting: oldKEK(newKEK), oldDEK(newDEK)
					toSend.add(new KeysJoinMessage(newKeks, newDek, oldKeks, oldDek));
					toSend.add(new InitMessage(joiner, newKeks, newDek, table.getPublicKey(joiner)));
				}
				
				for(Message m : toSend){
					layer.sendDown(m);
				}
				
				layer.sendDown(new DONEMessage());

				layer.setState(new DONEState(layer, members));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
