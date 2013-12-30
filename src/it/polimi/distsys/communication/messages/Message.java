package it.polimi.distsys.communication.messages;

import it.polimi.distsys.peers.Peer;

import java.io.Serializable;

public interface Message extends Serializable {

    public void display();
    
    public void execute(Peer peer);

    public Message unpack();
}
