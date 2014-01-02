package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.InterruptedMessage;
import it.polimi.distsys.communication.messages.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TCPLayer implements Layer {
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Layer layer;

	public TCPLayer(InputStream in, OutputStream out, Layer layer) {
		super();
		try {
			this.out = new ObjectOutputStream(out);
			this.in = new ObjectInputStream(in);
			this.layer = layer;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Message send(Message msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
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

		if (msg == null) {
			try {
				in.close();
				msg = new InterruptedMessage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (layer != null) {
			return layer.receive(msg);
		}

		return new ArrayList<Message>(Arrays.asList(msg));
	}

}
