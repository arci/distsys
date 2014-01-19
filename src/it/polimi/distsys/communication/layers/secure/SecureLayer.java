package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.components.Decrypter;
import it.polimi.distsys.communication.components.Encrypter;
import it.polimi.distsys.communication.components.TableException;
import it.polimi.distsys.communication.layers.Layer;
import it.polimi.distsys.communication.messages.EncryptedMessage;
import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;

public abstract class SecureLayer extends Layer {
	protected Encrypter enc;
	protected Decrypter dec;

	@Override
	public List<Message> processOnReceive(Message msg) throws IOException {
		EncryptedMessage encrypted = (EncryptedMessage) msg;
		Message m = null;
		try {
			m = dec.decryptMsg(encrypted);
		} catch (ClassNotFoundException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Message>(Arrays.asList(m));
	}

	@Override
	public List<Message> processOnSend(Message msg) {
		Message encrypted = null;
		try {
			encrypted = enc.encryptMsg(msg);
		} catch (IllegalBlockSizeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Message>(Arrays.asList(encrypted));
	}

	public boolean isForMe(UUID id) {
		return Peer.ID.equals(id);
	}

	public abstract void join(UUID memberID, Key publicKey) throws IOException, TableException;

	public abstract void leave(UUID memberID) throws IOException,
			TableException;

	public abstract void updateDEK(SealedObject dek);

	public abstract void updateKEK(SealedObject kek);

}
