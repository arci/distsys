package it.polimi.distsys.communication;

public class SequenceNumber implements MessageDecorator {
    private Message message;

    private SequenceNumber(Message message) {
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
