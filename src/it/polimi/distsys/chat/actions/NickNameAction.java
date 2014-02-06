package it.polimi.distsys.chat.actions;

import it.polimi.distsys.Printer;
import it.polimi.distsys.chat.Peer;

public class NickNameAction implements Action {

	@Override
	public void execute(Peer peer, String... params) {
		String message = "";
		String param = "";
		for(String p : params){
			param += p;
		}
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
