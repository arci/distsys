package it.polimi.distsys.chat;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.util.List;

public class Displayer implements Runnable {
	private Peer peer;

	public Displayer(Peer peer) {
		this.peer = peer;
	}

	@Override
	public void run() {
		while (true) {
			List<Message> messages = null;
			try {
				messages = peer.receive();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (Message m : messages) {
				m.display();
			}
		}
	}
}
