package it.polimi.distsys.components;

import java.io.Serializable;
import java.util.UUID;

public class SequenceNumber implements Serializable, Comparable<SequenceNumber> {
	private static final long serialVersionUID = -7309542130823400005L;
	private UUID clientID;
	private int messageID;

	public SequenceNumber(UUID clientID, int messageID) {
		super();
		this.clientID = clientID;
		this.messageID = messageID;
	}

	public UUID getClientID() {
		return clientID;
	}

	public int getMessageID() {
		return messageID;
	}

	@Override
	public String toString() {
		return clientID.toString().substring(0, 4) + "#" + messageID;
	}

	@Override
	public int compareTo(SequenceNumber other) {
		if (this.getClientID() != other.getClientID()) {
			return 0;
		}

		if (this.getMessageID() < other.getMessageID()) {
			return -1;
		}

		if (this.getMessageID() > other.getMessageID()) {
			return 1;
		}

		return 0;
	}
}
