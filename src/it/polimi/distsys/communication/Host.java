package it.polimi.distsys.communication;

import java.io.InputStream;

public interface Host {
	
	public InputStream getInputStream();
	
	public Sender getSender();
	
	public Receiver getReceiver();
	
	public String getName();
}
