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
		Commander commander = new Commander(peer);

		while (true) {
			String str = in.nextLine();
			if (str.equals("leave")) {
				break;
			}
			Message msg = new StringMessage(str);

			if (str.startsWith("/")) {
				commander.execute(str.substring(1));
			} else {
				peer.sendMulticast(msg);
			}
		}

		// Client client = (Client) peer;
		peer.sendMulticast(new LeaveMessage(peer.getID()));
		in.close();
	}
}