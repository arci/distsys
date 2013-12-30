package it.polimi.distsys.components;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Group {
	Set<Host> hosts;

	public Group() {
		super();
		hosts = new HashSet<Host>();
	}

	public void join(Host host) {
		hosts.add(host);
	}

	public void leave(Integer leaverID) {
		for(Host host : hosts){
			if(host.getID().equals(leaverID)){
				hosts.remove(host);
				break;
			}
		}
	}

	public synchronized Iterator<Host> iterator() {
		Set<Host> cloned = new HashSet<Host>(hosts);
		return cloned.iterator();
	}

	public synchronized int size() {
		return hosts.size();
	}
	
	@Override
	public String toString() {
		String str = null;
		for(Host host : hosts){
			str += host.getID() + "   ";
		}
		
		return str;
	}

}
