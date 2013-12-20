package it.polimi.distsys.communication;

public class StopMessage implements Message {

    @Override
    public void display() {
	// TODO Auto-generated method stub
	System.out.println("display on " + getClass().getCanonicalName());
    }

    @Override
    public Message unpack() {
	return this;
    }

}
