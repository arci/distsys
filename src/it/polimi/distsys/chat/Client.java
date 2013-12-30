package it.polimi.distsys.chat;

import it.polimi.distsys.communication.messages.ConnectionMessage;
import it.polimi.distsys.peers.Host;
import it.polimi.distsys.peers.Peer;

import java.io.IOException;
import java.net.Socket;

public class Client extends Peer {

	public Client(int accessPort, String serverAddress, int serverPort) {
		super(accessPort);
		try {
			connect(new Host(this, new Socket(serverAddress, serverPort)));
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
		sendUnicast(host, new ConnectionMessage(getAddress(), getListeningPort()));
	}

}