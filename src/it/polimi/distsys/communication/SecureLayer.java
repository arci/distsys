package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.CryptedMessage;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.StringMessage;
import it.polimi.distsys.components.Decrypter;
import it.polimi.distsys.components.Encrypter;
import it.polimi.distsys.components.FlatTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecureLayer extends Layer {
	private Encrypter enc;
	private Decrypter dec;
	private FlatTable table;
	
	public SecureLayer() {
		table = new FlatTable();
		enc = new Encrypter(table.getDEK());
		dec = new Decrypter(table.getDEK());
	}

	@Override
	public List<Message> processOnReceive(Message msg) throws IOException {
		CryptedMessage encrypted = (CryptedMessage) msg;
		String content = dec.decrypt(encrypted.getContent());
		return new ArrayList<Message>(Arrays.asList(new StringMessage(content)));
	}

	@Override
	public Message processOnSend(Message msg) {
		Message encrypted = new CryptedMessage(enc.encrypt(msg.toString()));
		return encrypted;
	}

	@Override
	public void join() throws IOException {
		// TODO Auto-generated method stub
		underneath.join();
	}

}
