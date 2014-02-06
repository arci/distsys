package it.polimi.distsys.chat;

import it.polimi.distsys.Config;

import java.io.IOException;

public class ServerLauncher {

	public static void main(String[] args) throws IOException {
		Config.init();
		ChatFrame chatFrame = ChatFrame.get();
		Server server = new Server();
		chatFrame.setNickname(server.getNickname());
		server.startDisplayer();
		server.startReader();

		Printer.printDebug(ServerLauncher.class, "Server ready");
		Printer.print("Server launched...");
	}

}
