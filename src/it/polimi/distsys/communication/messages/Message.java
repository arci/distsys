package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;

import java.io.Serializable;

public interface Message extends Serializable {

    public void display();
    
    public void onReceive(Layer layer);
    
    public void onSend(Layer layer);
    
	public Message unpack();
}
