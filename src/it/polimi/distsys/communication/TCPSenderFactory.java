package it.polimi.distsys.communication;

public class TCPSenderFactory {
    public Sender makeSender() {
	return new TCPSender();
    }
}
