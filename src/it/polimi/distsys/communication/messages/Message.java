package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.layers.Layer;

import java.io.IOException;
import java.io.Serializable;

public interface Message extends Serializable {

    public void display();
    
    public void onReceive(Layer layer) throws IOException;
    
    public void onSend(Layer layer);
    
	public Message unpack();
}
