package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.ApplicationLayer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.components.Printer;

import java.net.InetAddress;

public class ConnectionMessage implements Message {
	private static final long serialVersionUID = 6267101796372206458L;
	private InetAddress address;
	private int port;

	public ConnectionMessage(InetAddress address, int receptionistPort) {
		this.address = address;
		port = receptionistPort;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass().getCanonicalName() + " received:  " + this.toString());
	}

	@Override
	public Message unpack() {
		return this;
	}

	@Override
	public String toString() {
		return address.getHostAddress() + ":" + port;
	}

	@Override
	public void onReceive(Layer layer) {
//		receiver.setCommand(new ConnectCommand(address, port));
//		receiver.onReceive(sender);
		
		ApplicationLayer app = (ApplicationLayer) layer;
		app.connect(address, port);
	}
}
