package it.polimi.distsys.chat;


public class Demo {

	public static void main(String[] args) {
		String[] serverArgs = { "65000" };
		ServerLauncher.main(serverArgs);
		for (int i = 0; i < 3; i++) {
			Integer port = 1234 + i;
			String[] clientArgs = { port.toString(), "localhost", "65000" };
			ClientLauncher.main(clientArgs);
		}
	}

}
