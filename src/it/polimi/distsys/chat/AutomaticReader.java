package it.polimi.distsys.chat;

import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.StringMessage;

import java.io.IOException;
import java.util.Scanner;

public class AutomaticReader implements Runnable {
	private Peer peer;

	public AutomaticReader(Peer peer) {
		this.peer = peer;
	}

	@Override
	@SuppressWarnings("all")
	public void run() {
		Scanner in = new Scanner(System.in);
		int i = 0;

		while (true) {
			in.nextLine();
			Message msg = new StringMessage(" > Message " + i);
			try {
				peer.send(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
	}
}