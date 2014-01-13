package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.DEKMessage;
import it.polimi.distsys.communication.messages.KEKsMessage;
import it.polimi.distsys.components.FixedFlatTable;

import java.security.Key;
import java.util.List;
import java.util.UUID;

public class ServerSecureLayer extends SecureLayer {
	private FixedFlatTable table;
	
	public ServerSecureLayer() {
		super();
		table = new FixedFlatTable();
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
			sendDown(new KEKsMessage(memberID, table.updateKEKs(memberID)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateKEKs(List<Key> keks) {}

	@Override
	public void updateDEK(Key dek) {}

}
