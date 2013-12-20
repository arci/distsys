package it.polimi.distsys.communication;

public class CompleteSenderFactory {
	
	public Sender makeSender(){
		return new CausalSender(new ReliableSender(new Serializer(new TCPSender())));
	}
	
	public static void main(String[] args) {
		new CompleteSenderFactory().makeSender().send(null, new StringMessage("Message content"));
	}

}
