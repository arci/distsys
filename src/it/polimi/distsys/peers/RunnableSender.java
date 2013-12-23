package it.polimi.distsys.peers;

import it.polimi.distsys.communication.LeaveMessage;
import it.polimi.distsys.communication.Message;
import it.polimi.distsys.communication.Sender;
import it.polimi.distsys.communication.StringMessage;
import it.polimi.distsys.communication.TCPSenderFactory;
import it.polimi.distsys.security.Encrypter;

import java.io.IOException;

public class RunnableSender implements Runnable {
	private Encrypter encrypter;
	private Sender sender;

	public RunnableSender(Peer parent, Peer peer, Encrypter encrypter) {
		super();
		this.encrypter = encrypter;
		try {
			sender = new TCPSenderFactory().makeSender(peer.getOut());
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
			String str = System.console().readLine();
			if(str.equals("leave")){
				Message msg = new LeaveMessage();
				sender.send(msg);
				break;
			}
			Message msg = new StringMessage(str);
			sender.send(msg);
		}
	}

}
