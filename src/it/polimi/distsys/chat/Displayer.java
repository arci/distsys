package it.polimi.distsys.chat;

import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.components.Printer;

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

			for (Message m : messages) {
				Printer.print("Displayer says:");m.display();
			}
		}
	}
}
