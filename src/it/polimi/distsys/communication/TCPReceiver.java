package it.polimi.distsys.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class TCPReceiver implements Receiver {
	private BufferedReader in;
	private Receiver receiver;

	public TCPReceiver(InputStream in, Receiver receiver) {
		super();
		this.in = new BufferedReader(new InputStreamReader(in));
		this.receiver = receiver;
	}
	
	@Override
	public List<Message> receive(Message m) {
		Message msg = null;
		try {
			String string = in.readLine();
			System.out.println("TCP received: " + string);
			msg = new RawMessage(string);
//			
//			String className = string.split("#")[0];
//			msg = (Message) Class.forName(className).newInstance();
//			list.add(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return receiver.receive(msg);
	}

}
