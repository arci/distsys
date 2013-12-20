package it.polimi.distsys.communication;

public class TCPSender implements Sender {
	
	
	@Override
	public void send(Host host, Message msg) {
		// TODO Auto-generated method stub
		msg.notify();
	}

}
