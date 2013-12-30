package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.peers.Host;

import java.io.Serializable;

public interface Message extends Serializable {

    public void display();
    
    public void execute(Peer receiver, Host sender);

    public Message unpack();
}
