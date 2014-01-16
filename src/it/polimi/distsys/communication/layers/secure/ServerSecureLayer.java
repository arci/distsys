package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.components.Decrypter;
import it.polimi.distsys.communication.components.Encrypter;
import it.polimi.distsys.communication.components.FlatTable;
import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.components.TableException;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerSecureLayer extends SecureLayer {
	private FlatTable table;
	
	private ServerState state;
	private List<UUID> joiners = new ArrayList<UUID>();
	private List<UUID> leavers = new ArrayList<UUID>();
	
	
	public ServerSecureLayer() {
		super();
		table = new FlatTable();
		dek = table.getDEK();
		enc = new Encrypter(dek);
		dec = new Decrypter(dek);
		
		state = new NormalState(this);
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
	
	public void ACKReceived(UUID id) throws IOException, TableException {
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
	public void updateDEK(Key dek) {
		enc.updateKey(dek);
		dec.updateKey(dek);
	}
	
	@Override
	public void updateKEK(Integer position, Key key) {}

	@Override
	public void leave() throws IOException {
		//it shouldn't happen that the server leaves
	}
	
	public FlatTable getTable() {
		return table;
	}
	
	public void setState(ServerState state) {
		Printer.printDebug(getClass(), "state set to " + state.getClass().getSimpleName());
		this.state = state;
	}
	
	public void addJoiner(UUID id){
		joiners.add(id);
	}
	
	public void addLeaver(UUID id){
		leavers.add(id);
	}
	
	public List<UUID> getJoiners(){
		List<UUID> cloned = new ArrayList<UUID>(joiners);
		joiners.clear();
		return cloned;
	}
	
	public List<UUID> getLeavers(){
		List<UUID> cloned = new ArrayList<UUID>(leavers);
		leavers.clear();
		return cloned;
	}
}
