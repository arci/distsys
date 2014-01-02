package it.polimi.distsys.communication.factories;

import it.polimi.distsys.communication.ReliableLayer;
import it.polimi.distsys.communication.Stack;
import it.polimi.distsys.communication.TCPLayer;

import java.io.InputStream;
import java.io.OutputStream;

public class StackFactory {
	public static Stack makeTCPStack(InputStream in, OutputStream out) {
		TCPLayer tcp = new TCPLayer(in, out);
		return new Stack(tcp, tcp);
	}
	
	public static Stack makeTCPIDStack(InputStream in, OutputStream out) {
		TCPLayer tcp = new TCPLayer(in, out);
		ReliableLayer rel = new ReliableLayer();
		tcp.setAbove(rel);
		rel.setUnderneath(tcp);
		return new Stack(rel, tcp);
	}
}
