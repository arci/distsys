package it.polimi.distsys.communication;

public class CausalSender implements Sender {
	private Sender sender;
	private VectorClock clock;
	private int clientID;
	
	public CausalSender(Sender sender, int clientID) {
		super();
		this.sender = sender;
		this.clientID = clientID;
		this.clock = new VectorClock(clientID);
	}

	@Override
	public void send(Host host, Message msg) {
		
		Message causaMsg = new VectorClockMessage(msg, clock);
		sender.send(host, causaMsg);
	}

}
