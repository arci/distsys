package it.polimi.distsys.peers;

import it.polimi.distsys.security.Decrypter;
import it.polimi.distsys.security.Encrypter;

import javax.crypto.SecretKey;

public class SecureClient {

    private Encrypter encrypter;
    private Decrypter decrypter;

    public SecureClient(SecretKey dek) {
	encrypter = new Encrypter(dek);
	decrypter = new Decrypter(dek);
	new Thread(new RunnableSender(encrypter)).start();
	new Thread(new RunnableReceiver(decrypter)).start();
    }

    public void updateKey(SecretKey newKey) {
	encrypter.updateKey(newKey);
	decrypter.updateKey(newKey);
    }
}
