package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Printer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.secure.ServerSecureLayer;
import it.polimi.distsys.communication.secure.TableException;

import java.io.IOException;
import java.util.UUID;

public class ACKMessage implements Message {
	private static final long serialVersionUID = 2380820177699370771L;
	private UUID id;

	public ACKMessage(UUID id) {
		this.id = id;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public void onReceive(Layer layer) {
		layer.stopReceiving();
		try {

			ServerSecureLayer sec = (ServerSecureLayer) layer;
			try {
				sec.ACKReceived(id);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ClassCastException e) {
			// TODO: handle exception
			//ACKs are only for servers!
		}
	}

	@Override
	public void onSend(Layer layer) {
	}

	@Override
	public Message unpack() {
		return this;
	}

}
