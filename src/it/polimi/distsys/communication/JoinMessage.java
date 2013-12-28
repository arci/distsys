package it.polimi.distsys.communication;

import it.polimi.distsys.peers.Host;
import it.polimi.distsys.peers.Peer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class JoinMessage implements Message {
	private InetAddress address;
	private int port;
	private Peer peer;

	public JoinMessage(InetAddress address, int receptionistPort) {
		this.address = address;
		port = receptionistPort;
	}

	public JoinMessage() {
	}

	@Override
	public void display() {
		System.out.println(getClass().getCanonicalName() + " received!");
		try {
			if (!peer.isMe(address, port)) {
				peer.join(new Host(peer, new Socket(address, port), null));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Message unpack() {
		return this;
	}

	public void setPeer(Peer peer) {
		this.peer = peer;
	}

	public void setAddress(String address) {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.address = inetAddress;
	}

	public void setPort(String port) {
		int intPort = Integer.parseInt(port);
		this.port = intPort;
	}

	@Override
	public String toString() {
		return address.getHostAddress() + Deserializer.SEPARATOR + port;
	}

}
