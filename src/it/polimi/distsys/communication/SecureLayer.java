package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.EncryptedMessage;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.StringMessage;
import it.polimi.distsys.components.Decrypter;
import it.polimi.distsys.components.Encrypter;
import it.polimi.distsys.components.FlatTable;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class SecureLayer extends Layer {
	private Encrypter enc;
	private Decrypter dec;
	private Key dek;
	
	public SecureLayer() {
		dek = new FlatTable().getDEK();
		enc = new Encrypter(dek);
		dec = new Decrypter(dek);
	}

	@Override
	public List<Message> processOnReceive(Message msg) throws IOException {
		EncryptedMessage encrypted = (EncryptedMessage) msg;
		String content = dec.decrypt(encrypted.getContent());
		return new ArrayList<Message>(Arrays.asList(new StringMessage(content)));
	}

	@Override
	public Message processOnSend(Message msg) {
		Message encrypted = new EncryptedMessage(enc.encrypt(msg.toString()));
		return encrypted;
	}
	
	@Override
	public void join() throws IOException {
		underneath.join();
	}
	
	public abstract void join(UUID memberID);
	public abstract void leave(UUID memberID);
	public abstract void updateKEKs(List<Key> keks);
	public abstract void updateDEK(Key dek);

}
