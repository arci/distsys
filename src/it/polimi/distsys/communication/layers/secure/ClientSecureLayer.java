package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.components.Decrypter;
import it.polimi.distsys.communication.components.Encrypter;
import it.polimi.distsys.communication.components.FlatTable;
import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.messages.ACKMessage;
import it.polimi.distsys.communication.messages.JoinMessage;
import it.polimi.distsys.communication.messages.LeaveMessage;
import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientSecureLayer extends SecureLayer {
	private Key[] keks = new Key[FlatTable.BITS];

	private ClientState init;
	private ClientState stop;
	private ClientState state;

	public ClientSecureLayer() {
		super();
		enc = new Encrypter();
		dec = new Decrypter();

		init = new InitState(this);
		stop = new STOPState(this);
		state = init;
	}

	@Override
	public List<Message> processOnSend(Message msg) {
		if (state.send(msg)) {
			return super.processOnSend(msg);
		}
		return new ArrayList<Message>();
	}

	@Override
	public List<Message> processOnReceive(Message msg) throws IOException {
		if (state.receive(msg)) {
			return super.processOnReceive(msg);
		}
		return new ArrayList<Message>();
	}

	@Override
	public void join(UUID memberID) {}

	@Override
	public void join() throws IOException {
		underneath.join();
		sendDown(new JoinMessage(Peer.ID));
	}

	@Override
	public void leave(UUID memberID) {}

	@Override
	public void updateDEK(Key dek) {
		this.dek = dek;
		enc.updateKey(dek);
		dec.updateKey(dek);
	}
	
	@Override
	public void updateKEK(Integer position, Key key) {
		keks[position] = key;
	}

	@Override
	public void leave() throws IOException {
		sendDown(new LeaveMessage(Peer.ID));
	}

	public void sendACK() throws IOException {
		sendDown(new ACKMessage(Peer.ID));
	}

	public Key getKEK(int index) {
		return keks[index];
	}

	public void setState(ClientState state) {
		Printer.printDebug(getClass(), "state set to "
				+ state.getClass().getSimpleName());
		this.state = state;
	}

	public void keysReceived() throws IOException {
		state.keysReceived();
		for (Message m : init.getMessages()) {
			send(m);
		}
	}

	public void stop() throws IOException {
		state.stop();
	}

	public void done() throws IOException {
		state.done();
		for (Message m : stop.getMessages()) {
			send(m);
		}
	}
}
