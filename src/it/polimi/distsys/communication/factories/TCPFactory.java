package it.polimi.distsys.communication.factories;

import it.polimi.distsys.communication.Receiver;
import it.polimi.distsys.communication.Sender;
import it.polimi.distsys.communication.TCPReceiver;
import it.polimi.distsys.communication.TCPSender;

import java.io.InputStream;
import java.io.OutputStream;

public class TCPFactory {
	public Receiver makeReceiver(InputStream in) {
		return new TCPReceiver(in, null);
	}
	
	public Sender makeSender(OutputStream out) {
		return new TCPSender(out);
	}
}
