package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.VectorClock;
import it.polimi.distsys.peers.Host;
import it.polimi.distsys.peers.Peer;

public class VectorClockMessage implements MessageDecorator {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3674718470667194111L;
	private Message message;
    private VectorClock clock;

    public VectorClockMessage(Message message, VectorClock clock) {
    	super();
    	this.message=message;
    	this.clock = clock;
    	
    }

    @Override
    public void display() {

	System.out.println("display on " + getClass().getCanonicalName() + clock.toString());
    }

    @Override
    public Message unpack() {
	return message;
    }

	@Override
	public String toString() {
		return "VectorClockMessage [message=" + message + ", clock=" + clock
				+ "]";
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		// TODO Auto-generated method stub
		
	}

	
    
}
