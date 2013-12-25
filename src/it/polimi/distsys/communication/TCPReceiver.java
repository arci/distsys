package it.polimi.distsys.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TCPReceiver implements Receiver {
	private BufferedReader in;

	public TCPReceiver(InputStream in) {
		super();
		this.in = new BufferedReader(new InputStreamReader(in));
	}
	
	@Override
	public List<Message> receive() {
		Message msg = null;
		List<Message> list = new ArrayList<Message>();
		try {
			msg = new StringMessage(in.readLine());
			list.add(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
