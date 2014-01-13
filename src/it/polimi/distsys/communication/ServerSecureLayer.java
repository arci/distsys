package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.DEKMessage;
import it.polimi.distsys.communication.messages.KEKsMessage;
import it.polimi.distsys.components.Decrypter;
import it.polimi.distsys.components.Encrypter;
import it.polimi.distsys.components.FixedFlatTable;

import java.security.Key;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ServerSecureLayer extends SecureLayer {
	private FixedFlatTable table;
	
	public ServerSecureLayer() {
		super();
		table = new FixedFlatTable();
		dek = table.getDEK();
		enc = new Encrypter(dek);
		dec = new Decrypter(dek);
	}

	@Override
	public void join(UUID memberID) {
		try {
			table.join(memberID);
			sendDown(new KEKsMessage(memberID, table.getKEKs(memberID)));
			sendDown(new DEKMessage(table.getDEK()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void leave(UUID memberID) {
		try {
			sendDown(new DEKMessage(table.refreshDEK()));
			table.leave(memberID);
			Iterator<UUID> itr = table.iterator();
			
			while(itr.hasNext()){
				sendDown(new KEKsMessage(itr.next(), table.updateKEKs(memberID)));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateKEKs(List<Key> keks) {}

	@Override
	public void updateDEK(Key dek) {
		enc.updateKey(dek);
		dec.updateKey(dek);
	}

}
