package it.polimi.distsys.communication;

import java.security.Key;
import java.util.List;
import java.util.UUID;

public class ServerSecureLayer extends SecureLayer {

	@Override
	public void join(UUID memberID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leave(UUID memberID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateKEKs(List<Key> keks) {}

	@Override
	public void updateDEK(Key dek) {}

}
