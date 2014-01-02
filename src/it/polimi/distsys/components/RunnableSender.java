package it.polimi.distsys.components;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.security.Encrypter;

import java.util.List;

@SuppressWarnings("unused")
public class RunnableSender implements Runnable {
	private Encrypter encrypter;
	private Host host;
	private Layer layer;

	public RunnableSender(Host host, Layer layer, Encrypter encrypter) {
		super();
		this.encrypter = encrypter;
		this.host = host;
		this.layer = layer;
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
				layer.send(m);
			}
		}
	}

}
