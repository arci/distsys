package it.polimi.distsys.chat.actions;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.components.Host;

public class StartingIDCommand implements Command {
	private Integer ID;
	
	public StartingIDCommand(Integer ID) {
		this.ID = ID;
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		receiver.setID(ID);
	}

}
