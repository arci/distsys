package it.polimi.distsys.peers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
	List<Peer> peers;
	
	public Group() {
		super();
		peers = Collections.synchronizedList(new ArrayList<Peer>());
	}

	public void join(Peer peer) {
		peers.add(peer);
	}
	
	public void leave(Peer peer){
		peers.remove(peer);
	}

}
