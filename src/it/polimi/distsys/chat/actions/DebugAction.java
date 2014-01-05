package it.polimi.distsys.chat.actions;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.components.Printer;

public class DebugAction implements Action {

	@Override
	public void execute(Peer peer, String param) {
		boolean value = Boolean.parseBoolean(param);
		Peer.DEBUG = value;
		Printer.print(">>> debug: " + value);
	}

}
