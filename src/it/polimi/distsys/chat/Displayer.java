package it.polimi.distsys.chat;

import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.peers.Peer;

import java.util.List;

public class Displayer implements Runnable {
	private Peer peer;

	public Displayer(Peer peer) {
		this.peer = peer;
	}

	@Override
	public void run() {
		while (true) {
			List<Message> messages = peer.getIncomingMessages();
			// Iterator<Host> itr = group.iterator();
			//
			// while (itr.hasNext()) {
			// for (Message m : messages) {
			// itr.next().notifyObservers(m);
			// }
			// }

			for (Message m : messages) {
				m.display();
				m.execute(peer);
			}
		}
	}
}
