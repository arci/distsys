package it.polimi.distsys.chat;

import java.util.Arrays;

import it.polimi.distsys.Config;
import it.polimi.distsys.chat.actions.Action;
import it.polimi.distsys.chat.actions.EchoAction;

public class Commander {
	private Peer peer;

	public Commander(Peer peer) {
		super();
		this.peer = peer;
	}

	public void execute(String string) {
		String[] parts = string.split(" ");
		String key = parts[0];

		Action action = null;
		try {
			String className = Config.getAction(key);
			action = (Action) Class.forName(className).newInstance();
			parts = Arrays.copyOfRange(parts, 1, parts.length);
			action.execute(peer, parts);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			new EchoAction().execute(peer, key + ": command not found");
		}
	}
}
