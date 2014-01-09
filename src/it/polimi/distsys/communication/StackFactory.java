package it.polimi.distsys.communication;


import java.io.IOException;
import java.net.UnknownHostException;

public class StackFactory {
	public static Stack makeMultiStack() throws UnknownHostException {
		//ApplicationLayer app = new ApplicationLayer(null, null);
		MulticastLayer tcp = new MulticastLayer();
		//tcp.setAbove(app);
		//app.setUnderneath(tcp);
		return new Stack(tcp, tcp);
	}
	
	public static Stack makeRelMultiStack() throws IOException {
		//ApplicationLayer app = new ApplicationLayer(coordinator, host);
		MulticastLayer tcp = new MulticastLayer();
		ReliableLayer rel = new ReliableLayer();
		tcp.setAbove(rel);
		rel.setUnderneath(tcp);
		//rel.setAbove(app);
		//app.setUnderneath(rel);
		return new Stack(rel, tcp);
	}
}
