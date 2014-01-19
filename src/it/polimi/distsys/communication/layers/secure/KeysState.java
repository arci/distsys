package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.components.FlatTable;
import it.polimi.distsys.communication.components.TableException;
import it.polimi.distsys.communication.messages.DEKMessage;
import it.polimi.distsys.communication.messages.DONEMessage;
import it.polimi.distsys.communication.messages.InitMessage;
import it.polimi.distsys.communication.messages.KEKMessage;
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
		waitingACK.add(id);
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
					Key oldDek = table.getDEK();
					Key newDek = table.refreshDEK();
					layer.updateDEK(newDek);
					Key[] oldKeks = table.getKEKs(leaver);
					Key[] newKeks = table.updateKEKs(leaver);
					Key[] remainingKeks = table.getOtherKEKs(leaver);
					table.leave(leaver);
					for (int i = 0; i < oldKeks.length; i++) {
						// encrypting: oldDEK(oldKEK(newKEK))
						toSend.add(new KEKMessage(newKeks[i], oldKeks[i],
								oldDek));
					}
					
					//encrypting: remainingKEKs(newDEK)
					for(Key k : remainingKeks){
						toSend.add(new DEKMessage(newDek, k));
					}
				}

				for (UUID joiner : joiners) {
					Key oldDek = table.getDEK();
					Key newDek = table.refreshDEK();
					layer.updateDEK(newDek);
					table.join(joiner);
					Key[] oldKeks = table.getKEKs(joiner);
					Key[] newKeks = table.updateKEKs(joiner);
					for (int i = 0; i < oldKeks.length; i++) {
						// encrypting: oldKEK(newKEK)
						toSend.add(new KEKMessage(newKeks[i], oldKeks[i],
								null));
					}
					// encrypting: oldDEK(newDEK)
					toSend.add(new DEKMessage(newDek, oldDek));
				}
				
				Key lastDek = table.getDEK();
				for(UUID joiner : joiners){
					Key[] keks = table.getKEKs(joiner);
					toSend.add(new InitMessage(keks, lastDek, table.getPublicKey(joiner)));
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
