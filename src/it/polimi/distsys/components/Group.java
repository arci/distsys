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
		hosts.remove(getMemberByID(leaverID));
	}

	public synchronized Iterator<Host> iterator() {
		Set<Host> cloned = new HashSet<Host>(hosts);
		return cloned.iterator();
	}

	public synchronized int size() {
		return hosts.size();
	}

	public Host getMemberByID(int ID) {
		for (Host host : hosts) {
			if (host.getID() == ID) {
				return host;
			}
		}

		return null;
	}

	public void setMemberID(Host sender, Integer ID) {
		for (Host host : hosts) {
			if (host.equals(sender)) {
				host.setID(ID);
			}
		}
	}

	@Override
	public String toString() {
		String str = "";
		for (Host host : hosts) {
			str += host.getID() + "   ";
		}

		return str;
	}

}