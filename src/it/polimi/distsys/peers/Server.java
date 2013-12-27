package it.polimi.distsys.peers;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import it.polimi.distsys.communication.JoinMessage;
import it.polimi.distsys.communication.Message;
import it.polimi.distsys.communication.StringMessage;

public class Server extends Peer {

	public Server(int port) {
		super(port);
	}

	@Override
	public void leave(Host host) {
		// TODO Auto-generated method stub
		super.leave(host);
	}

	@Override
	public void onJoin(Host host) {
		Peer.addOutgoingMessage(new JoinMessage(host.getAddress(), host.getPort()));
	}

	@Override
	public void update(Object o) {
		// default behavior is set to string message
		StringMessage m = (StringMessage) o;
		update(m);
	}

	public void update(StringMessage m) {
		// no action on string message
		System.out.println("StringMessage received!");
	}

	public void update(JoinMessage m) {
		// perform a join
		System.out.println("JoinMessage received!");
	}
	
	//TODO remove... Only to see things work
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

	//TODO remove... Only to see things work
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
}
