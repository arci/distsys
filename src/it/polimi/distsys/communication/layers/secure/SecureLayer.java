package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.components.Decrypter;
import it.polimi.distsys.communication.components.Encrypter;
import it.polimi.distsys.communication.layers.Layer;
import it.polimi.distsys.communication.messages.EncryptedMessage;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.StringMessage;

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
	public List<Message> processOnSend(Message msg) {
		try {
			StringMessage strmsg = (StringMessage) msg;
			Message encrypted = new EncryptedMessage(enc.encrypt(strmsg.toString()));
			return new ArrayList<Message>(Arrays.asList(encrypted));
		} catch (ClassCastException e) {
			return new ArrayList<Message>(Arrays.asList(msg));
		}
	}
	
	public boolean isForMe(UUID id){
		return Peer.ID.equals(id);
	}
	
	public abstract void join(UUID memberID);
	public abstract void leave(UUID memberID);
	public abstract void updateKEKs(List<Key> keks);
	public abstract void updateDEK(Key dek);

}
