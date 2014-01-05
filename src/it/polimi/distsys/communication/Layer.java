package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;

import java.util.ArrayList;
import java.util.List;

public abstract class Layer {
	private Layer above;
	private Layer underneath;
	private boolean sendUp;
	private boolean sendDown;
	
	public Layer() {
		sendUp = true;
		sendDown = true;
	}
	
	public void send(Message msg){
		msg.onSend(this);
		if(sendDown){
			msg = processOnSend(msg);
			sendDown(msg);
		}
		sendDown = true;
	}

	public List<Message> receive(List<Message> msgs){
		List<Message> toReceive = new ArrayList<Message>();
		
		for(Message m : msgs){
			m.onReceive(this);
			if(sendUp){
				toReceive.addAll(processOnReceive(m));
			}
			sendUp = true;
		}
		
		return sendUp(toReceive);
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

	public abstract List<Message> processOnReceive(Message msg);
	public abstract Message processOnSend(Message msg);

}
