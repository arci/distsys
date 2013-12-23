package it.polimi.distsys.communication;

import java.io.IOException;

public class TCPSender implements Sender {
	
	
	@Override
	public void send(Host host, Message msg) {
		// TODO Auto-generated method stub
		try {
			host.getReceiver().
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
