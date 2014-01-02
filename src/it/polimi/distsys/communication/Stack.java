package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stack {
	List<Layer> layers;
	List<Layer> reversedLayers;

	public Stack(List<Layer> layers) {
		super();
		this.layers = layers;
		reversedLayers = new ArrayList<Layer>(layers);
		Collections.reverse(reversedLayers);
	}
	
	public void send(Message msg){
		for(Layer l : layers){
			msg = l.send(msg);
		}
	}

	public List<Message> receive(List<Message> msgs){
		for(Layer l : reversedLayers){
			msgs = l.receive(msgs);
		}
		
		return msgs;
	}
	
	

}
