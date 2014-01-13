package it.polimi.distsys.communication;

import it.polimi.distsys.chat.Peer;

import java.io.IOException;
import java.net.UnknownHostException;

public class StackFactory {
	public static Stack makeMultiStack() throws UnknownHostException {
		MulticastLayer tcp = new MulticastLayer();
		return new Stack(tcp, tcp);
	}

	public static Stack makeRelMultiStack() throws IOException {
		MulticastLayer tcp = new MulticastLayer();
		ReliableLayer rel = new ReliableLayer();
		tcp.setAbove(rel);
		rel.setUnderneath(tcp);
		return new Stack(rel, tcp);
	}

	public static Stack makeCausRelMultiStack() throws IOException {
		CausalLayer caus = new CausalLayer();
		MulticastLayer tcp = new MulticastLayer();
		ReliableLayer rel = new ReliableLayer();
		tcp.setAbove(rel);
		rel.setUnderneath(tcp);
		rel.setAbove(caus);
		caus.setUnderneath(rel);
		return new Stack(caus, tcp);
	}

	public static Stack makeSecCausRelMultiStack() throws IOException {
		SecureLayer sec = null;
		if (Peer.IS_SERVER) {
			sec = new ServerSecureLayer();
		} else {
			sec = new ClientSecureLayer();
		}
		CausalLayer caus = new CausalLayer();
		MulticastLayer tcp = new MulticastLayer();
		ReliableLayer rel = new ReliableLayer();
		tcp.setAbove(rel);
		rel.setUnderneath(tcp);
		rel.setAbove(caus);
		caus.setUnderneath(rel);
		caus.setAbove(sec);
		sec.setUnderneath(caus);
		return new Stack(sec, tcp);
	}
}
