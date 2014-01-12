package it.polimi.distsys.communication;

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
		Message strmsg = new StringMessage(dec.decrypt(msg.toString()));
		return new ArrayList<Message>(Arrays.asList(strmsg));
	}

	@Override
	public Message processOnSend(Message msg) {
		Message strmsg = new StringMessage(enc.encrypt(msg.toString()));
		return strmsg;
	}

	@Override
	public void join() throws IOException {
		// TODO Auto-generated method stub
		underneath.join();
	}

}
