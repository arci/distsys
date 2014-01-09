package it.polimi.distsys.communication.factories;

import it.polimi.distsys.communication.Stack;
import it.polimi.distsys.communication.TCPLayer;

import java.net.UnknownHostException;

public class StackFactory {
	public static Stack makeTCPStack() throws UnknownHostException {
		//ApplicationLayer app = new ApplicationLayer(null, null);
		TCPLayer tcp = new TCPLayer();
		//tcp.setAbove(app);
		//app.setUnderneath(tcp);
		return new Stack(tcp, tcp);
	}
	
//	public static Stack makeTCPIDStack(Peer coordinator, Host host, InputStream in, OutputStream out) {
//		ApplicationLayer app = new ApplicationLayer(coordinator, host);
//		TCPLayer tcp = new TCPLayer(in, out);
//		ReliableLayer rel = new ReliableLayer();
//		tcp.setAbove(rel);
//		rel.setUnderneath(tcp);
//		rel.setAbove(app);
//		app.setUnderneath(rel);
//		return new Stack(app, tcp);
//	}
}
