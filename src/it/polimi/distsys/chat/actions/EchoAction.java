package it.polimi.distsys.chat.actions;

import it.polimi.distsys.chat.Peer;

public class EchoAction implements Action {

	@Override
	public void execute(Peer peer, String... param) {
		System.out.println(">>> " + param);
	}

}
