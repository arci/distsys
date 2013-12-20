package it.polimi.distsys.communication;

public class ReliableSender implements Sender {
	private Sender sender;

	public ReliableSender(Sender sender) {
		super();
		this.sender = sender;
	}

	@Override
	public void send(Host host, Message msg) {
		// TODO Auto-generated method stub
		System.out
				.println(getClass().getName() + " sending: " + msg.toString());
		sender.send(host, msg);
	}

}
