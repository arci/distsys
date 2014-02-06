package it.polimi.distsys.chat;

import it.polimi.distsys.Config;

import java.io.IOException;

public class ClientLauncher {
	public static void main(String[] args) throws IOException {
		ChatFrame chatFrame = ChatFrame.get();
		Config.init();
		Printer.print("Welcome to SGC! Now type your messages...");
		Client client = new Client();
		chatFrame.setNickname(client.getNickname());
		client.startDisplayer();
		client.startReader();
		Printer.printDebug(ClientLauncher.class, "Client ready");
	}
}
