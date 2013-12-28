package it.polimi.distsys.communication;

import it.polimi.distsys.peers.Peer;

import java.io.InputStream;

public class TCPReceiverFactory {
	public Receiver makeReceiver(Peer peer, InputStream in) {
		return new TCPReceiver(in, new Deserializer(peer, null));
	}
}
