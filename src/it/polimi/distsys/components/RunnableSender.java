package it.polimi.distsys.components;

import it.polimi.distsys.communication.Sender;
import it.polimi.distsys.communication.factories.ReliableFactory;
import it.polimi.distsys.communication.factories.TCPFactory;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.security.Encrypter;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("unused")
public class RunnableSender implements Runnable {
	private Encrypter encrypter;
	private Sender sender;
	private Host host;

	public RunnableSender(Host host, Encrypter encrypter) {
		super();
		this.encrypter = encrypter;
		this.host = host;
		try {
			sender = new ReliableFactory().makeSender(this.host.getOut());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				sender.send(m);
			}
		}
	}

}
