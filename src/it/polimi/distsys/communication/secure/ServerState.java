package it.polimi.distsys.communication.secure;


import java.io.IOException;
import java.security.Key;
import java.util.UUID;

public interface ServerState {
	public void join(UUID id, Key publicKey) throws IOException, TableException;

	public void leave(UUID id) throws IOException, TableException;

	public void ACKReceived(UUID id) throws IOException, TableException;
}
