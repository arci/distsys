package it.polimi.distsys.chat.actions;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.messages.MissMessage;

public class MissAction implements Action {

	@Override
	public void execute(Peer peer, String param) {
		peer.sendMulticast(new MissMessage(param));
	}

}
