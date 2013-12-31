package it.polimi.distsys.chat;

import it.polimi.distsys.communication.messages.ConnectionMessage;
import it.polimi.distsys.communication.messages.IDMessage;
import it.polimi.distsys.components.Host;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Peer {
	private Host server;

	public Client(int accessPort, String serverAddress, int serverPort) {
		super(accessPort);
		try {
			this.server = new Host(Server.DEFAULT_ID, this, new Socket(
					serverAddress, serverPort));
			join(server);
			sendUnicast(server, new ConnectionMessage(getAddress(),
					getListeningPort()));
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

	public Host getServer() {
		return server;
	}

	@Override
	public void onJoin(int ID, InetAddress address, int port) {
		try {
			Host joiner = new Host(ID, this, new Socket(address, port));
			join(joiner);
			sendUnicast(joiner, new IDMessage(getID()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void onID(Host host, int ID){
		group.setMemberID(host, ID);
	}
}