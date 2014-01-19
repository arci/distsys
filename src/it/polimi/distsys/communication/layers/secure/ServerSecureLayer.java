package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.components.Decrypter;
import it.polimi.distsys.communication.components.Encrypter;
import it.polimi.distsys.communication.components.FlatTable;
import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.components.TableException;

import java.io.IOException;
import java.security.Key;
import java.util.UUID;

import javax.crypto.SealedObject;

public class ServerSecureLayer extends SecureLayer {
	private FlatTable table;
	private ServerState state;

	public ServerSecureLayer() {
		super();
		table = new FlatTable();
		enc = new Encrypter(table.getDEK());
		dec = new Decrypter(table.getDEK());

		state = new NormalState(this);
	}

	@Override
	public void join() throws IOException {
		underneath.join();
	}
	
	@Override
	public void join(UUID memberID, Key publicKey) throws IOException, TableException {
		state.join(memberID, publicKey);
	}

	@Override
	public void leave(UUID memberID) throws IOException, TableException {
		state.leave(memberID);
	}
	
	public void ACKReceived(UUID id) throws IOException, TableException {
		state.ACKReceived(id);
	}
	
	@Override
	public void leave() throws IOException {
		// it shouldn't happen that the server leaves
	}

	public FlatTable getTable() {
		return table;
	}

	public void setState(ServerState state) {
		Printer.printDebug(getClass(), "\t\t\t\tSTATE SET TO "
				+ state.getClass().getSimpleName().toUpperCase());
		this.state = state;
	}

	@Override
	public void updateDEK(SealedObject dek) {}

	@Override
	public void updateKEK(SealedObject kek) {}
	
	public void updateDEK(Key dek) {
		enc.setKey(dek);
		dec.setKey(dek);
	}
}
