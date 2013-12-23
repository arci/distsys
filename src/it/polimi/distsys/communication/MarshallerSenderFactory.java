package it.polimi.distsys.communication;

public class MarshallerSenderFactory {
    public Sender makeSender() {
	return new Serializer(new TCPReceiver());
    }
}
