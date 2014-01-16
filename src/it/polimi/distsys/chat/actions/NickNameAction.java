package it.polimi.distsys.chat.actions;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.components.Printer;

public class NickNameAction implements Action {

	@Override
	public void execute(Peer peer, String param) {
		String message = "";
		if (param.isEmpty()) {
			message = "cannot set an empty nickname";
		} else {
			param = param.replace(" ", "_");
			message = "nickname changed to " + param;
			peer.setNickname(param);
		}
		Printer.print(message);
	}

}
