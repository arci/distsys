package it.polimi.distsys.components;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.Stack;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.security.Decrypter;

import java.util.List;

@SuppressWarnings("unused")
public class RunnableReceiver implements Runnable {
	private Decrypter decrypter;
	private Host host;
	private Stack stack;

	public RunnableReceiver(Host host, Stack stack, Decrypter decrypter) {
		super();
		this.decrypter = decrypter;
		this.host = host;
		this.stack = stack;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Message in = receiver.receive().get(0);
		// byte[] msgInByte = decrypter.decrypt(in.toString());
		// System.out.println(getClass().getName() + " says: "
		// + msgInByte.toString());

		while (host.isActive()) {
			List<Message> msgs = stack.receive(null);
			host.addIncomingMessages(msgs);
		}

		System.out.println("Client has left");
	}

}
