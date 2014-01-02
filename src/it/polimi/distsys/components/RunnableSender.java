package it.polimi.distsys.components;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.Stack;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.security.Encrypter;

import java.util.List;

@SuppressWarnings("unused")
public class RunnableSender implements Runnable {
	private Encrypter encrypter;
	private Host host;
	private Stack stack;

	public RunnableSender(Host host, Stack stack, Encrypter encrypter) {
		super();
		this.encrypter = encrypter;
		this.host = host;
		this.stack = stack;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// System.out.println(getClass().getName() +
		// " says: sending message...");
		// Message msg = new StringMessage("Message taken from stdin");
		// byte[] msgInByte = encrypter.encrypt(msg.toString());
		// //sender.send(null, new StringMessage(msgInByte.toString()));
		while (true) {
			List<Message> messages = host.getOutgoingMessages();

			for (Message m : messages) {
				stack.send(m);
			}
		}
	}

}
