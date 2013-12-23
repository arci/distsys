package it.polimi.distsys.communication;

public class TCPReceiverFactory {
    public Receiver makeReceiver() {
	return new TCPReceiver();
    }
}
