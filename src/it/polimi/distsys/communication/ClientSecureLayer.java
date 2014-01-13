package it.polimi.distsys.communication;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.messages.LeaveMessage;
import it.polimi.distsys.components.Decrypter;
import it.polimi.distsys.components.Encrypter;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.UUID;

public class ClientSecureLayer extends SecureLayer {
	private List<Key> keks;
	
	public ClientSecureLayer() {
		super();
		enc = new Encrypter();
		dec = new Decrypter();
	}

	@Override
	public void join(UUID memberID) {}

	@Override
	public void leave(UUID memberID) {}

	@Override
	public void updateKEKs(List<Key> keks) {
		this.keks = keks;
	}

	@Override
	public void updateDEK(Key dek) {
		this.dek = dek;
		enc.updateKey(dek);
		dec.updateKey(dek);
	}
	
	public Key getKEK(int index){
		return keks.get(index);
	}

	@Override
	public void leave() throws IOException {
		sendDown(new LeaveMessage(Peer.ID));
	}
}
