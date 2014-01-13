package it.polimi.distsys.communication;

import java.security.Key;
import java.util.List;
import java.util.UUID;

public class ClientSecureLayer extends SecureLayer {

	@Override
	public void join(UUID memberID) {}

	@Override
	public void leave(UUID memberID) {}

	@Override
	public void updateKEKs(List<Key> keks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDEK(Key dek) {
		// TODO Auto-generated method stub
		
	}
}
