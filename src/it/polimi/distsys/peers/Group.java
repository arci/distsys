package it.polimi.distsys.peers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Group {
	List<Host> hosts;

	public Group() {
		super();
		hosts = Collections.synchronizedList(new ArrayList<Host>());
	}

	public void join(Host host) {
		hosts.add(host);
	}

	public void leave(Host host) {
		hosts.remove(host);
	}

	public synchronized Iterator<Host> iterator() {
		List<Host> cloned = new ArrayList<Host>(hosts);
		return cloned.iterator();
	}

	public synchronized int size() {
		return hosts.size();
	}

}
