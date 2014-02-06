package it.polimi.distsys.chat.actions;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.chat.Printer;

public class EchoAction implements Action {

	@Override
	public void execute(Peer peer, String... param) {
		String toprint = "";
		for(String s : param){
			toprint += (s + " ");
		}
		Printer.print(">>> " + toprint);
	}

}
