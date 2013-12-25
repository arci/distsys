package it.polimi.distsys.peers;

import it.polimi.distsys.communication.JoinMessage;
import it.polimi.distsys.communication.Message;
import it.polimi.distsys.communication.StringMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Scanner;

public class Client extends Peer {
	private Host host;

	public Client(int accessPort, String serverAddress, int serverPort) {
		super(accessPort);
		group = new Group();
		try {
			host = new Host(new Socket(serverAddress, serverPort), "ciaobau");
			join(host);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void read() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Scanner in = new Scanner(System.in);

				while (true) {
					String str = in.nextLine();
					if (str.equals("leave")) {
						// Message msg = new LeaveMessage();
						break;
					}
					Message msg = new StringMessage(str);

					Iterator<Host> it = group.iterator();

					while (it.hasNext()) {
						it.next().send(msg);
					}
				}

				in.close();

			}
		}).start();
	}

	@Override
	protected void onJoin(Host host) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Object o) {
		// default behavior is set to string message
		StringMessage m = (StringMessage) o;
		update(m);
	}

	public void update(StringMessage m) {
		m.display();
	}

	public void update(JoinMessage m) {

	}
}
