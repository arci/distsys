package it.polimi.distsys.peers;

import it.polimi.distsys.security.Decrypter;
import it.polimi.distsys.security.Encrypter;


public class SecureClient {

	public static void main(String[] args) {
		new Thread(new RunnableSender(new Encrypter())).start();
		new Thread(new RunnableReceiver(new Decrypter())).start();
	}
}
