package it.polimi.distsys.communication;

public class VectorClock implements MessageDecorator {
    private Message message;

    private VectorClock(Message message) {
	super();
	this.message = message;
    }

    @Override
    public void display() {
	// TODO Auto-generated method stub
	System.out.println("display on " + getClass().getCanonicalName());
    }

    @Override
    public Message unpack() {
	return message;
    }

}
