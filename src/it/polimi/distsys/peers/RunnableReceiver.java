package it.polimi.distsys.peers;

import it.polimi.distsys.communication.CompleteReceiverFactory;
import it.polimi.distsys.communication.Message;
import it.polimi.distsys.communication.Receiver;
import it.polimi.distsys.security.Decrypter;

public class RunnableReceiver implements Runnable {
    private Decrypter decrypter;
    private Receiver receiver;

    public RunnableReceiver(Decrypter decrypter) {
	super();
	this.decrypter = decrypter;
	receiver = new CompleteReceiverFactory().makeReceiver();
    }

    @Override
    public void run() {
	// TODO Auto-generated method stub
	Message in = receiver.receive().get(0);
	byte[] msgInByte = decrypter.decrypt(in.toString());
	System.out.println(getClass().getName() + " says: "
		+ msgInByte.toString());
    }

}
