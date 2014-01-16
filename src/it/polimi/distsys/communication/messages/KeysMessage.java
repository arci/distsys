package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.layers.Layer;
import it.polimi.distsys.communication.layers.secure.ClientSecureLayer;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class KeysMessage implements Message {
	private static final long serialVersionUID = -8156497824267452282L;
	private Map<UUID, List<Integer>> receivers;
	private Key dek;
	private Key[] keks;

	public KeysMessage(Map<UUID, List<Integer>> receivers, Key dek, Key[] keks) {
		super();
		this.receivers = receivers;
		this.dek = dek;
		this.keks = keks;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public void onReceive(Layer layer) {
		layer.stopReceiving();
		ClientSecureLayer sec = (ClientSecureLayer) layer;
		sec.updateDEK(dek);
		for (UUID receiver : receivers.keySet()) {
			if (sec.isForMe(receiver)) {
				for(Integer position: receivers.get(receiver)){
					sec.updateKEK(position, keks[position]);
				}
				break;
			}
		}
		
		try {
			sec.keysReceived();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message unpack() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String toString() {
		return dek.getEncoded().toString();
	}

}
