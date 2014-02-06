package it.polimi.distsys.chat;

import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.StringMessage;

import java.io.IOException;

public class Reader implements Runnable {
	private static ChatFrame chatFrame = ChatFrame.get();
	private Peer peer;

	public Reader(Peer peer) {
		this.peer = peer;
	}

	@Override
	public void run() {
		Commander commander = new Commander(peer);

		while (true) {
			String str = "";
			try {
				str = chatFrame.getMessage();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (str.equals("leave")) {
				break;
			}
			Message msg = new StringMessage(peer.getNickname() + "> " + str);

			if (str.startsWith("/")) {
				commander.execute(str.substring(1));
			} else {
				try {
					peer.send(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		try {
			peer.leave();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.exit(0);
	}
}