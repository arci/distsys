package it.polimi.distsys.peers;

import it.polimi.distsys.communication.Message;
import it.polimi.distsys.communication.StringMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client extends Peer {

	public Client(int accessPort, String serverAddress, int serverPort) {
		super(accessPort);
		group = new Group();
		try {
			join(new Host(this, new Socket(serverAddress, serverPort), null));
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
					// Iterator<Host> itr = group.iterator();
					//
					// while (itr.hasNext()) {
					// for (Message m : messages) {
					// itr.next().notifyObservers(m);
					// }
					// }

					for (Message m : messages) {
						m.display();
					}
				}
			}
		}).start();
	}

	@Override
	public void onJoin(Host host) {
		// TODO Auto-generated method stub
		
	}

}
