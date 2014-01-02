package it.polimi.distsys.chat.actions;

import it.polimi.distsys.chat.Peer;

public interface Action {

	public void execute(Peer peer, String param);

}
