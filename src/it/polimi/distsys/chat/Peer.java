package it.polimi.distsys.chat;

import it.polimi.distsys.communication.Stack;
import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public abstract class Peer {
	public static boolean DEBUG = true;
	public static final UUID ID = UUID.randomUUID();
	public static boolean IS_SERVER = false;
	protected String nickname;
	protected Stack stack;

	public Peer() throws IOException {
		super();
		nickname = "sgcc#" + ID.toString().substring(0, 4);
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
