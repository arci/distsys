package it.polimi.distsys.communication;

public class CompleteReceiverFactory implements ReceiverFactory {

	@Override
	public Receiver makeReceiver() {
		return new TCPReceiver(new Deserializer(new CausalReceiver(new ReliableReceiver(null))));
	}
	
	public static void main(String[] args) {
		new CompleteReceiverFactory().makeReceiver().receive().get(0).display();
	}

}
