package it.polimi.distsys.communication;

public class TCPSender implements Sender {
	@Override
	public void send(Host host, Message msg) {
		// TODO Auto-generated method stub
		System.out
				.println(getClass().getName() + " sending: " + msg.toString() + "... The message is on the net...");
	}

}
