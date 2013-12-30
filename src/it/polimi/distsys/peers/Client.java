package it.polimi.distsys.peers;

import java.io.IOException;
import java.net.Socket;

public class Client extends Peer {

	public Client(int accessPort, String serverAddress, int serverPort) {
		super(accessPort);
		group = new Group();
		try {
			join(new Host(this, new Socket(serverAddress, serverPort)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startReader() {
		new Thread(new Reader(this)).start();
	}

	public void startDisplayer() {
		new Thread(new Displayer(this)).start();
	}

	@Override
	public void onJoin(Host host) {
		// TODO Auto-generated method stub

	}
}