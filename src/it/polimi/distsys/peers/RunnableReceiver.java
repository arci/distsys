package it.polimi.distsys.peers;

import it.polimi.distsys.communication.Receiver;
import it.polimi.distsys.communication.TCPReceiverFactory;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.security.Decrypter;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("unused")
public class RunnableReceiver implements Runnable {
	private Decrypter decrypter;
	private Receiver receiver;
	private Host host;

	public RunnableReceiver(Host host, Decrypter decrypter) {
		super();
		this.decrypter = decrypter;
		this.host = host;
		try {
			receiver = new TCPReceiverFactory().makeReceiver(this.host.getIn());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Message in = receiver.receive().get(0);
		// byte[] msgInByte = decrypter.decrypt(in.toString());
		// System.out.println(getClass().getName() + " says: "
		// + msgInByte.toString());

		while (true) {
			List<Message> msgs = receiver.receive(null);
			if (msgs == null)
				break;
			host.addIncomingMessages(msgs);
		}

		System.out.println("Client has left");
	}

}
