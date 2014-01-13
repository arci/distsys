package it.polimi.distsys.communication;

import java.security.Key;
import java.util.List;
import java.util.UUID;

public class ClientSecureLayer extends SecureLayer {
	private List<Key> keks;
	

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
	}
	
	public Key getKEK(int index){
		return keks.get(index);
	}
}
