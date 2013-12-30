package it.polimi.distsys.chat;

import it.polimi.distsys.communication.messages.LeaveMessage;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.StringMessage;

import java.util.Scanner;

public class Reader implements Runnable {
	private Peer peer;

	public Reader(Peer peer) {
		this.peer = peer;
	}
	
	@Override
	public void run() {
		Scanner in = new Scanner(System.in);

		while (true) {
			String str = in.nextLine();
			if (str.equals("leave")) {
				// Message msg = new LeaveMessage();
				break;
			}
			Message msg = new StringMessage(str);

			peer.sendMulticast(msg);
		}

		Client client = (Client) peer;
		client.sendUnicast(client.getServer(), new LeaveMessage(peer.getID()));
		in.close();

	}
}