package it.polimi.distsys.peers;

import it.polimi.distsys.communication.CompleteSenderFactory;
import it.polimi.distsys.communication.Message;
import it.polimi.distsys.communication.Sender;
import it.polimi.distsys.communication.StringMessage;
import it.polimi.distsys.security.Encrypter;

public class RunnableSender implements Runnable {
    private Encrypter encrypter;
    private Sender sender;

    public RunnableSender(Encrypter encrypter) {
	super();
	this.encrypter = encrypter;
	sender = new CompleteSenderFactory().makeSender();
    }

    @Override
    public void run() {
	// TODO Auto-generated method stub
	System.out.println(getClass().getName() + " says: sending message...");
	Message msg = new StringMessage("Message taken from stdin");
	byte[] msgInByte = encrypter.encrypt(msg.toString());
	sender.send(null, new StringMessage(msgInByte.toString()));
    }

}
