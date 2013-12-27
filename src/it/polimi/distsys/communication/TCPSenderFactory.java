package it.polimi.distsys.communication;

import java.io.OutputStream;

public class TCPSenderFactory {
	public Sender makeSender(OutputStream out) {
		return new Serializer(new TCPSender(out));
	}
}
