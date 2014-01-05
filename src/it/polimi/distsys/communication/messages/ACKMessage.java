package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;

public class ACKMessage implements Message {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5782706440666041170L;

	@Override
    public void display() {
	// TODO Auto-generated method stub
	System.out.println("display on " + getClass().getCanonicalName());
    }

    @Override
    public Message unpack() {
	return this;
    }

	@Override
	public void onReceive(Layer layer) {
		// TODO Auto-generated method stub
		
	}

	
}
