package it.polimi.distsys.communication;

import it.polimi.distsys.chat.Client;
import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.chat.Server;
import it.polimi.distsys.communication.messages.IDMessage;
import it.polimi.distsys.communication.messages.JoinMessage;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.StartingIDMessage;
import it.polimi.distsys.components.Host;
import it.polimi.distsys.components.Printer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicationLayer extends Layer {
	private Peer peer;
	private Host host;
	
	public ApplicationLayer(Peer peer, Host host) {
		super();
		this.peer = peer;
		this.host = host;
	}

	@Override
	public void send(Message msg) {
		sendDown(msg);
	}

	@Override
	public List<Message> process(Message msg) {
		return new ArrayList<Message>(Arrays.asList(msg));
	}
	
	public void connect(InetAddress address, int port){
		Server server = (Server) peer;
		Integer ID = server.incrementID(); 
		server.sendUnicast(host, new StartingIDMessage(ID));
		server.getGroup().join(host);
		server.getGroup().setMemberID(host, ID);
		server.sendExceptOne(host, new JoinMessage(ID, address, port));
	}
	
	public void startingID(Integer ID){
		peer.setID(ID);
		Printer.printDebug("My ID is now " + ID);
	}
	
	public void id(Integer ID){
		Client client = (Client) peer;
		client.getGroup().setMemberID(host, ID);
	}
	
	public void join(Integer ID, InetAddress address, int port){
		try {
			Host joiner = new Host(ID, peer, new Socket(address, port));
			peer.getGroup().join(joiner);
			//TODO remove
			Printer.printDebug("My group is now: " + peer.getGroup().toString());
			peer.sendUnicast(joiner, new IDMessage(peer.getID()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void leave(Integer leaverID){
		peer.getGroup().leave(leaverID);
		// TODO remove
		Printer.printDebug("My group is: " + peer.getGroup().toString());
	}

}
