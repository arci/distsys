package it.polimi.distsys.peers;

import java.io.IOException;
import java.util.List;

import it.polimi.distsys.communication.Message;
import it.polimi.distsys.communication.Receiver;
import it.polimi.distsys.communication.TCPReceiverFactory;
import it.polimi.distsys.security.Decrypter;

public class RunnableReceiver implements Runnable {
	private Decrypter decrypter;
	private Receiver receiver;

	public RunnableReceiver(Peer parent, Host host, Decrypter decrypter) {
		super();
		this.decrypter = decrypter;
		try {
			receiver = new TCPReceiverFactory().makeReceiver(host.getIn());
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
		
		while(true){
			List<Message> msgs = receiver.receive();
			for(Message m : msgs){
				m.display();
			}
		}
	}

}
