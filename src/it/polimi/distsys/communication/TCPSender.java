package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class TCPSender implements Sender {
	private ObjectOutputStream out;

	public TCPSender(OutputStream out) {
		super();
		try {
			this.out = new ObjectOutputStream(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void send(Message msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
