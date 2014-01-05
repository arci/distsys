package it.polimi.distsys.communication.factories;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.ApplicationLayer;
import it.polimi.distsys.communication.ReliableLayer;
import it.polimi.distsys.communication.Stack;
import it.polimi.distsys.communication.TCPLayer;
import it.polimi.distsys.components.Host;

import java.io.InputStream;
import java.io.OutputStream;

public class StackFactory {
	public static Stack makeTCPStack(Peer coordinator, Host host, InputStream in, OutputStream out) {
		ApplicationLayer app = new ApplicationLayer(coordinator, host);
		TCPLayer tcp = new TCPLayer(in, out);
		tcp.setAbove(app);
		app.setUnderneath(tcp);
		return new Stack(app, tcp);
	}
	
	public static Stack makeTCPIDStack(Peer coordinator, Host host, InputStream in, OutputStream out) {
		ApplicationLayer app = new ApplicationLayer(coordinator, host);
		TCPLayer tcp = new TCPLayer(in, out);
		ReliableLayer rel = new ReliableLayer();
		tcp.setAbove(rel);
		rel.setUnderneath(tcp);
		rel.setAbove(app);
		app.setUnderneath(rel);
		return new Stack(app, tcp);
	}
}
