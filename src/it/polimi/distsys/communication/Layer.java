package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;

import java.util.ArrayList;
import java.util.List;

public abstract class Layer {
	protected Layer above;
	protected Layer underneath;
	protected boolean sendUp;
	protected boolean sendDown;
	protected List<Message> toReceive;
	
	public Layer() {
		sendUp = true;
		sendDown = true;
		toReceive = new ArrayList<Message>();
	}
	
	public void send(Message msg){
		msg.onSend(this);
		if(sendDown){
			onSend(msg);
		}
		sendDown = true;
	}

	public List<Message> receive(List<Message> msgs){
		List<Message> cloned = new ArrayList<Message>(toReceive);
		toReceive.clear();
		
		for(Message m : msgs){
			m.onReceive(this);
			if(sendUp){
				cloned.addAll(onReceive(m));
			}
			sendUp = true;
		}
		
		return cloned;
	}
	
	public void stopReceiving(){
		sendUp = false;
	}
	
	public void stopSending(){
		sendDown = false;
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

	public abstract List<Message> onReceive(Message msg);
	public abstract void onSend(Message msg);

}
