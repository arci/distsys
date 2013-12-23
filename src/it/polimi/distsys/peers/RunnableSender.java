package it.polimi.distsys.peers;

import it.polimi.distsys.communication.Message;
import it.polimi.distsys.communication.Sender;
import it.polimi.distsys.communication.TCPSenderFactory;
import it.polimi.distsys.security.Encrypter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RunnableSender implements Runnable {
	private Encrypter encrypter;
	private Sender sender;
	private Host host;
	private List<Message> outgoing;

	public RunnableSender(Host host, Encrypter encrypter) {
		super();
		this.encrypter = encrypter;
		this.host = host;
		outgoing = new ArrayList<Message>();
		try {
			sender = new TCPSenderFactory().makeSender(this.host.getOut());
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
			List<Message> cloned = null;
			synchronized (this) {
				cloned = new ArrayList<Message>(outgoing);
				outgoing = new ArrayList<Message>();
			}

			for (Message m : cloned) {
				sender.send(m);
			}
		}
	}

	public synchronized void send(Message msg) {
		outgoing.add(msg);
	}

}
