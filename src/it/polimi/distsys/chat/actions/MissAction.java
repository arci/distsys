package it.polimi.distsys.chat.actions;

import java.io.IOException;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.messages.MissMessage;

public class MissAction implements Action {

	@Override
	public void execute(Peer peer, String param) {
		try {
			peer.send(new MissMessage(param));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}