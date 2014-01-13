package it.polimi.distsys.communication;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.messages.EncryptedMessage;
import it.polimi.distsys.communication.messages.JoinMessage;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.StringMessage;
import it.polimi.distsys.components.Decrypter;
import it.polimi.distsys.components.Encrypter;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class SecureLayer extends Layer {
	protected Encrypter enc;
	protected Decrypter dec;
	protected Key dek;

	@Override
	public List<Message> processOnReceive(Message msg) throws IOException {
		EncryptedMessage encrypted = (EncryptedMessage) msg;
		String content = dec.decrypt(encrypted.getContent());
		return new ArrayList<Message>(Arrays.asList(new StringMessage(content)));
	}

	@Override
	public Message processOnSend(Message msg) {
		try {
			StringMessage strmsg = (StringMessage) msg;
			Message encrypted = new EncryptedMessage(enc.encrypt(strmsg.toString()));
			return encrypted;
		} catch (ClassCastException e) {
			return msg;
		}
	}
	
	@Override
	public void join() throws IOException {
		underneath.join();
		sendDown(new JoinMessage(Peer.ID));
	}
	
	public boolean isForMe(UUID id){
		return Peer.ID.equals(id);
	}
	
	public abstract void join(UUID memberID);
	public abstract void leave(UUID memberID);
	public abstract void updateKEKs(List<Key> keks);
	public abstract void updateDEK(Key dek);

}
