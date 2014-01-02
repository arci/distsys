package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.components.Host;

public class Signature implements MessageDecorator {
	
	private static final long serialVersionUID = 1500524929829982625L;
	private Message message;
	private Host sender;

	public Signature(Host sender, Message message) {
		super();
		this.message = message;
		this.sender = sender;
	}

	@Override
	public void display() {
		message.display();
	}

	@Override
	public Message unpack() {
		return message;
	}
	
	public Host getSender() {
		return sender;
	}

	@Override
	public void onReceive(Peer receiver, Host sender) {
		message.onReceive(receiver, sender);
	}

}
