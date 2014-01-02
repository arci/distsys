package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.components.Host;

public class StringMessage implements Message {
	private static final long serialVersionUID = 9052245167443004983L;
	private String content;

	public StringMessage(String content) {
		super();
		this.content = content;
	}

	@Override
	public void display() {
		System.err.println(content);
	}

	@Override
	public String toString() {
		return content;
	}

	@Override
	public Message unpack() {
		return this;
	}

	@Override
	public void onReceive(Peer receiver, Host sender) {
		// TODO Auto-generated method stub
		
	}

}
