package it.polimi.distsys.communication.messages;

import it.polimi.distsys.peers.Host;
import it.polimi.distsys.peers.Peer;

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
	public void execute(Peer receiver, Host sender) {
		// TODO Auto-generated method stub
		
	}


}
