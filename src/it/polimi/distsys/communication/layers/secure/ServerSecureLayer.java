package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.components.Decrypter;
import it.polimi.distsys.communication.components.Encrypter;
import it.polimi.distsys.communication.components.FlatTable;
import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.components.TableException;

import java.io.IOException;
import java.security.Key;
import java.util.UUID;

public class ServerSecureLayer extends SecureLayer {
	private FlatTable table;
	private ServerState state;

	public ServerSecureLayer() {
		super();
		table = new FlatTable();
		dek = table.getDEK();
		enc = new Encrypter(dek);
		dec = new Decrypter(dek);

		state = new NormalState(this);
	}

	@Override
	public void join() throws IOException {
		underneath.join();
	}
	
	@Override
	public void join(UUID memberID) throws IOException, TableException {
		state.join(memberID);
	}

	@Override
	public void leave(UUID memberID) throws IOException, TableException {
		state.leave(memberID);
	}
	
	public void ACKReceived(UUID id) throws IOException, TableException {
		state.ACKReceived(id);
	}

	@Override
	public void updateDEK(Key dek) {
		enc.updateKey(dek);
		dec.updateKey(dek);
	}

	@Override
	public void updateKEK(Integer position, Key key) {}

	@Override
	public void leave() throws IOException {
		// it shouldn't happen that the server leaves
	}

	public FlatTable getTable() {
		return table;
	}

	public void setState(ServerState state) {
		Printer.printDebug(getClass(), "state set to "
				+ state.getClass().getSimpleName());
		this.state = state;
	}
}
