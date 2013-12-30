package it.polimi.distsys.chat;

import it.polimi.distsys.communication.messages.LeaveMessage;

public class Server extends Peer {
	public static final Integer DEFAULT_ID = 0;
	private static Integer clientID = 1;

	public Server(int port) {
		super(port);
		ID = 0;
	}

	// TODO remove... Only to see things work
	public void startReader() {
		new Thread(new Reader(this)).start();
	}

	public void startDisplayer() {
		new Thread(new Displayer(this)).start();
	}

	public Integer incrementID() {
		int temp = clientID;
		clientID++;
		return temp;
	}

	@Override
	public void onLeave(Integer leaverID) {
		sendMulticast(new LeaveMessage(leaverID));
	}
}
