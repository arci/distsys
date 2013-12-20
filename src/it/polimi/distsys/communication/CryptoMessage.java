package it.polimi.distsys.communication;

public class CryptoMessage implements MessageDecorator {
    private Message message;

    private CryptoMessage(Message message) {
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
