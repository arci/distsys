package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;

public class StopMessage implements Message {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8154107478233299470L;

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

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub
		
	}


}
