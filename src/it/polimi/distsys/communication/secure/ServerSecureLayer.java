package it.polimi.distsys.communication.secure;

import it.polimi.distsys.Printer;

import java.io.IOException;
import java.security.Key;
import java.util.UUID;

public class ServerSecureLayer extends SecureLayer {
	public static boolean DEBUG;
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
	
	public void join(UUID memberID, Key publicKey) throws IOException, TableException {
		state.join(memberID, publicKey);
	}

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
	
	public void updateDEK(Key dek) {
		enc.setKey(dek);
		dec.setKey(dek);
	}
}
