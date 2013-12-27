package it.polimi.distsys.peers;

import it.polimi.distsys.communication.JoinMessage;
import it.polimi.distsys.communication.Message;
import it.polimi.distsys.communication.StringMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Client extends Peer {
    private Host host;
    private List<Host> others;

    public Client(int accessPort, String serverAddress, int serverPort) {
	super(accessPort);
	others = new ArrayList<Host>();
	try {
	    host = new Host(new Socket(serverAddress, serverPort), "ciaobau");
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
    public void update(Message m) {
	// TODO Auto-generated method stub

    }

    public void update(StringMessage m) {
	m.display();
    }

    public void update(JoinMessage m) {
	try {
	    Host host = new Host(new Socket(m.getAddress(),
		    m.getReceptionistPort()), m.getHostName());
	    others.add(host);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();

	}
    }
}
