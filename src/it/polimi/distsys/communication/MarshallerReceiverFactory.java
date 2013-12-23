package it.polimi.distsys.communication;

public class MarshallerReceiverFactory {
    public Receiver makeReceiver() {
	return new Deserializer(new TCPReceiver());
    }
}
