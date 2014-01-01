package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.InterruptedMessage;
import it.polimi.distsys.communication.messages.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TCPReceiver implements Receiver {
	private ObjectInputStream in;
	private Receiver receiver;

	public TCPReceiver(InputStream in, Receiver receiver) {
		super();
		try {
			this.in = new ObjectInputStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.receiver = receiver;
	}

	@Override
	public List<Message> receive(Message m) {
		Message msg = null;
		try {
			msg = (Message) in.readObject();
			//
			// String className = string.split("#")[0];
			// msg = (Message) Class.forName(className).newInstance();
			// list.add(msg);
		} catch (EOFException e) {
			Thread.currentThread().interrupt();

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(msg == null){
			try {
				in.close();
				msg = new InterruptedMessage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (receiver != null) {
			return receiver.receive(msg);
		}

		return new ArrayList<Message>(Arrays.asList(msg));
	}

}
