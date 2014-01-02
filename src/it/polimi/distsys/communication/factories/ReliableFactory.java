package it.polimi.distsys.communication.factories;

import java.io.InputStream;
import java.io.OutputStream;

import it.polimi.distsys.communication.Receiver;
import it.polimi.distsys.communication.ReliableReceiver;
import it.polimi.distsys.communication.ReliableSender;
import it.polimi.distsys.communication.Sender;
import it.polimi.distsys.communication.TCPReceiver;
import it.polimi.distsys.communication.TCPSender;

public class ReliableFactory {
	
	public Sender makeSender(OutputStream out){
		return new ReliableSender(new TCPSender(out));
	}
	
	public Receiver makeReceiver(InputStream in){
		return new TCPReceiver(in, new ReliableReceiver());
	}
}
