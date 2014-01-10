package it.polimi.distsys.communication;

import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.VectorClockMessage;
import it.polimi.distsys.components.VectorClock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CausalLayer extends Layer {
	private UUID id;
	private VectorClock vc;
	
	public CausalLayer() {
		id = UUID.randomUUID();
		vc = new VectorClock(id);
	}

	@Override
	public List<Message> processOnReceive(Message msg) throws IOException {
		// TODO Auto-generated method stub
		return new ArrayList<Message>(Arrays.asList(msg.unpack()));
	}

	@Override
	public Message processOnSend(Message msg) {
		vc.increment();
		Message toSend = new VectorClockMessage(vc, msg);
		return toSend;
	}

	@Override
	public void join() throws IOException {
		underneath.join();
	}

}
