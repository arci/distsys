package it.polimi.distsys.communication;

public class CompleteReceiverFactory {

	public Receiver makeReceiver() {
		return new TCPReceiver(new Deserializer(new CausalReceiver(new ReliableReceiver(null))));
	}
	
	//TODO remove
	public static void main(String[] args) {
		new CompleteReceiverFactory().makeReceiver().receive().get(0).display();
	}

}
