package it.polimi.distsys.communication;

public class VectorClockMessage implements MessageDecorator {
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

	
    
    
}
