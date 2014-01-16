package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.components.TableException;

import java.io.IOException;
import java.util.UUID;

public interface ServerState {
	public void join(UUID id) throws IOException, TableException;

	public void leave(UUID id) throws IOException, TableException;

	public void ACKReceived(UUID id) throws IOException, TableException;
}
