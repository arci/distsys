package it.polimi.distsys.communication;

import java.io.OutputStream;
import java.io.PrintWriter;

public class TCPSender implements Sender {
	private PrintWriter out;

	public TCPSender(OutputStream out) {
		super();
		this.out = new PrintWriter(out);
	}

	@Override
	public void send(Message msg) {
		out.println(msg.toString());
		out.flush();
	}

}
