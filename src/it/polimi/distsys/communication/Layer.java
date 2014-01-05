package it.polimi.distsys.communication;

import java.util.ArrayList;
import java.util.List;

import it.polimi.distsys.communication.messages.Message;

public abstract class Layer {
	protected Layer above;
	protected Layer underneath;
	protected boolean sendUp;
	protected List<Message> toReceive;
	
	public Layer() {
		sendUp = true;
		toReceive = new ArrayList<Message>();
	}

	public List<Message> receive(List<Message> msgs){
		List<Message> cloned = new ArrayList<Message>(toReceive);
		toReceive.clear();
		
		for(Message m : msgs){
			m.onReceive(this);
			if(sendUp){
				cloned.addAll(process(m));
			}
			sendUp = true;
		}
		
		return cloned;
	}
	
	public void stop(){
		sendUp = false;
	}
	
	public void setAbove(Layer above) {
		this.above = above;
	}

	public void setUnderneath(Layer underneath) {
		this.underneath = underneath;
	}
	
	public List<Message> sendUp(List<Message> msgs){
		return above.receive(msgs);
	}
	
	public void sendDown(Message msg){
		underneath.send(msg);
	}
	
	public abstract void send(Message msg);
	public abstract List<Message> process(Message msg);

}
