package it.polimi.distsys.peers;


public class Server extends Peer {

	public Server(int port) {
		super(port);
	}
	
	@Override
	public void leave(Host host) {
		// TODO Auto-generated method stub
		super.leave(host);
	}

	@Override
	public void doStuff() {
		//TODO send to everybody the joint peer		
	}
}
