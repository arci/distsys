package it.polimi.distsys.communication.messages;

import it.polimi.distsys.peers.Peer;

public class LeaveMessage implements Message {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8167138982859361874L;

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
	public void execute(Peer peer) {
		// TODO Auto-generated method stub
		
	}

}
