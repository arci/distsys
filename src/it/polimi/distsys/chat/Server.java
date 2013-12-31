package it.polimi.distsys.chat;

import it.polimi.distsys.communication.messages.JoinMessage;
import it.polimi.distsys.communication.messages.StartingIDMessage;
import it.polimi.distsys.components.Host;

import java.net.InetAddress;


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
	public void onJoin(int ID, InetAddress address, int port) {
		// TODO Auto-generated method stub
		
	}

	public void onConnect(Host sender, InetAddress address, int port) {
		Integer ID = incrementID(); 
		sendUnicast(sender, new StartingIDMessage(ID));
		sender.setID(ID);
		join(sender);
		sendExceptOne(sender, new JoinMessage(ID, address, port));	
	}

	
}
