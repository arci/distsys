package it.polimi.distsys.chat;

import it.polimi.distsys.communication.messages.ConnectionMessage;
import it.polimi.distsys.peers.Host;

import java.io.IOException;
import java.net.Socket;

public class Client extends Peer {
	private Host server;

	public Client(int accessPort, String serverAddress, int serverPort) {
		super(accessPort);
		try {
			this.server = new Host(Server.DEFAULT_ID, this, new Socket(
					serverAddress, serverPort));
			connect(server);
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

	public void connect(Host host) {
		group.join(host);
		sendUnicast(host, new ConnectionMessage(getAddress(),
				getListeningPort()));
	}
	
	public Host getServer() {
		return server;
	}

	@Override
	public void onLeave(Integer leaverID) {
		// TODO Auto-generated method stub

	}

}