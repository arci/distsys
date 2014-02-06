package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.causal.VectorClock;

public class VectorClockMessage implements MessageDecorator {
	private static final long serialVersionUID = 3674718470667194111L;
	private Message message;
	private VectorClock clock;

	public VectorClockMessage(VectorClock clock, Message message) {
		super();
		this.message = message;
		this.clock = clock;

	}

	public VectorClock getClock() {
		return clock;
	}

	@Override
	public void display() {

	}

	@Override
	public Message unpack() {
		return message;
	}

	@Override
	public String toString() {
		return "[VC: " + clock.toString() + ", " + message.getClass().getSimpleName() + "]";
	}

	@Override
	public void onReceive(Layer layer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub

	}

}
