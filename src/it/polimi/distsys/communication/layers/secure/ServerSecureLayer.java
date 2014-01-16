package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.components.Decrypter;
import it.polimi.distsys.communication.components.Encrypter;
import it.polimi.distsys.communication.components.FixedFlatTable;
import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.components.TableException;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.UUID;

public class ServerSecureLayer extends SecureLayer {
	private FixedFlatTable table;
	private ServerState normal;
	private ServerState stopping;
	private ServerState keys;
	
	private ServerState state;
	
	
	public ServerSecureLayer() {
		super();
		table = new FixedFlatTable();
		dek = table.getDEK();
		enc = new Encrypter(dek);
		dec = new Decrypter(dek);
		
		normal = new NormalState(this);
		stopping = new StoppingState(this);
		keys = new KeysState(this);
		state = normal;
	}

	@Override
	public void join(UUID memberID) {
		try {
			state.join(memberID);
		} catch (IOException | TableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ACKReceived(UUID id) throws IOException {
		state.ACKReceived(id);
	}
	
	@Override
	public void join() throws IOException {
		underneath.join();
	}

	@Override
	public void leave(UUID memberID) {
		try {
			state.leave(memberID);
		} catch (IOException | TableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateKEKs(List<Key> keks) {}

	@Override
	public void updateDEK(Key dek) {
		enc.updateKey(dek);
		dec.updateKey(dek);
	}

	@Override
	public void leave() throws IOException {
		//it shouldn't happen that the server leaves
	}
	
	public FixedFlatTable getTable() {
		return table;
	}
	
	public void setState(ServerState state) {
		Printer.printDebug(getClass(), "state set to " + state.getClass().getSimpleName());
		this.state = state;
	}
	
	public ServerState getNormalState() {
		return normal;
	}
	
	public ServerState getKeysState() {
		return keys;
	}
	
	public ServerState getStoppingState() {
		return stopping;
	}
}
