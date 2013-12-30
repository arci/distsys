package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.peers.Host;

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
	public void execute(Peer receiver, Host sender) {
		// TODO Auto-generated method stub
		
	}

	
}
