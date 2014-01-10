package it.polimi.distsys.communication;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.messages.Message;
import it.polimi.distsys.communication.messages.VectorClockMessage;
import it.polimi.distsys.components.Printer;
import it.polimi.distsys.components.VectorClock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CausalLayer extends Layer {
	private final UUID id = Peer.ID;
	private VectorClock vc;
	private Set<VectorClockMessage> receiving;

	public CausalLayer() {
		vc = new VectorClock(id);
		receiving = new HashSet<VectorClockMessage>();
	}
	
	@Override
	public Message processOnSend(Message msg) {
		vc.increment();
		Message toSend = new VectorClockMessage(vc, msg);
		return toSend;
	}

	@Override
	public List<Message> processOnReceive(Message msg) throws IOException {		
		List<Message> toReceive = new ArrayList<Message>();

		VectorClockMessage vcmsg = (VectorClockMessage) msg;
		VectorClock receivedVC = vcmsg.getClock();

		Set<VectorClockMessage> toItr = new HashSet<VectorClockMessage>(
				receiving);
		for (VectorClockMessage inQueue : toItr) {
			if (receivedVC.compareTo(inQueue.getClock()) == 0) {
				toReceive.add(inQueue.unpack());
				receiving.remove(inQueue);
			}
		}

		if (vc.compareTo(receivedVC) == -1) {
			Printer.printDebug(getClass(), "too new, let's queue it");
			receiving.add(vcmsg);
		} else {
			Printer.printDebug(getClass(), "it's ok, let's send it");
			toReceive.add(vcmsg.unpack());
			vc.merge(receivedVC);
		}
		return toReceive;
	}

	@Override
	public void join() throws IOException {
		underneath.join();
	}

}
