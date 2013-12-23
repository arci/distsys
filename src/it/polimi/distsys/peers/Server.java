package it.polimi.distsys.peers;

import it.polimi.distsys.communication.JoinMessage;
import it.polimi.distsys.communication.StringMessage;

import java.util.Iterator;

public class Server extends Peer {

	public Server(int port) {
		super(port);
	}

	@Override
	public void leave(Host host) {
		// TODO Auto-generated method stub
		super.leave(host);
	}

	@Override
	public void onJoin(Host host) {
		Iterator<Host> it = group.iterator();

		while (it.hasNext()) {
			Host nextHost = it.next();
			if (!nextHost.equals(host)) {
				nextHost.send(new JoinMessage(host.getAddress(), host.getPort()));
			}
		}
	}

	@Override
	public void update(Object o) {
		// default behavior is set to string message
		StringMessage m = (StringMessage) o;
		update(m);
	}

	public void update(StringMessage m) {
		// no action on string message
		System.out.println("StringMessage received!");
	}

	public void update(JoinMessage m) {
		// perform a join
		System.out.println("I received a JoinMessage!");
	}
}
