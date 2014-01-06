package it.polimi.distsys.chat;

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
		String param = "";
		if (parts.length >= 2) {
			for (int i = 1; i < parts.length; i++) {
				param += parts[i] + " ";
			}

			param = param.substring(0, param.length() - 1);
		}

		Action action = null;
		try {
			String className = Config.getAction(key);
			action = (Action) Class.forName(className).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			action = new EchoAction();
			param = key + ": command not found";
		}

		action.execute(peer, param);
	}

}
