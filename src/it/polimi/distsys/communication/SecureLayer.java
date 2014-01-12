package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecureLayer extends Layer {

	@Override
	public List<Message> processOnReceive(Message msg) throws IOException {
		// TODO Auto-generated method stub
		return new ArrayList<Message>(Arrays.asList(msg));
	}

	@Override
	public Message processOnSend(Message msg) {
		// TODO Auto-generated method stub
		return msg;
	}

	@Override
	public void join() throws IOException {
		// TODO Auto-generated method stub
		underneath.join();
	}

}
