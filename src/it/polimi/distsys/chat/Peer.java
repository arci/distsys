package it.polimi.distsys.chat;

import it.polimi.distsys.communication.Stack;
import it.polimi.distsys.communication.factories.StackFactory;
import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.util.List;

public abstract class Peer {
	public static boolean DEBUG = true;
	protected String nickname;
	protected Integer ID;
	protected Stack stack;

	public Peer() throws IOException {
		super();
		stack = StackFactory.makeTCPIDStack();
		stack.join();
	}
	
	public void send(Message m) throws IOException{
		stack.send(m);
	}
	
	public List<Message> receive() throws IOException{
		return stack.receive(null);
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return nickname;
	}
}
