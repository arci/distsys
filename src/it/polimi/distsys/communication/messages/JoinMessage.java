package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.ApplicationLayer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.components.Printer;

import java.net.InetAddress;

public class JoinMessage implements Message {
	private static final long serialVersionUID = 6267101796372206458L;
	private InetAddress address;
	private int port;
	private Integer ID;

	public JoinMessage(Integer ID, InetAddress address, int receptionistPort) {
		this.address = address;
		port = receptionistPort;
		this.ID = ID;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
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
		ApplicationLayer app = (ApplicationLayer) layer;
		app.join(ID, address, port);
	}

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub
		
	}
}
