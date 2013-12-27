package it.polimi.distsys.communication;

import java.io.InputStream;

public class TCPReceiverFactory {
	public Receiver makeReceiver(InputStream in) {
		return new TCPReceiver(in, new Deserializer(null));
	}
}
