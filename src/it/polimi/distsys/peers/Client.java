package it.polimi.distsys.peers;

import it.polimi.distsys.communication.JoinMessage;
import it.polimi.distsys.communication.Message;
import it.polimi.distsys.communication.StringMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Client extends Peer {
	private Host host;

	public Client(int accessPort, String serverAddress, int serverPort) {
		super(accessPort);
		group = new Group();
		try {
			host = new Host(new Socket(serverAddress, serverPort), null);
			join(host);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startReader() {
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

					addOutgoingMessage(msg);
				}

				in.close();

			}
		}).start();
	}

	public void startDisplayer() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					List<Message> messages = getIncomingMessages();
					Iterator<Host> itr = group.iterator();

					while (itr.hasNext()) {
						for (Message m : messages) {
							itr.next().notifyObservers(m);
						}
					}
				}
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
