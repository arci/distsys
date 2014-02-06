package it.polimi.distsys.communication.causal;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class VectorClock implements Serializable, Comparable<VectorClock>,
		Iterable<UUID> {
	private static final long serialVersionUID = 1019945114852671821L;
	private Map<UUID, Integer> vector;
	private UUID clientID;

	public VectorClock(UUID clientID) {
		super();
		this.clientID = clientID;
		vector = Collections.synchronizedMap(new HashMap<UUID, Integer>());
		vector.put(clientID, 0);
	}

	/**
	 * update number of client in the corresponding position
	 */
	public synchronized void increment() {
		int value = vector.get(clientID);
		value++;
		vector.put(clientID, value);
	}

	/**
	 * 
	 * @param v
	 *            merge the received vector clock with the local one
	 * 
	 */
	public void merge(VectorClock v) {
		Iterator<UUID> iterator = v.iterator();
		Map<UUID, Integer> cloned = new HashMap<UUID, Integer>(vector);
		while (iterator.hasNext()) {
			UUID key = iterator.next();
			Integer value = cloned.get(key);
			Integer otherValue = v.getScalar(key);
			if (value == null) {
				cloned.put(key, otherValue);
				value = otherValue;
			}

			if (value < otherValue) {
				cloned.put(key, otherValue);
			}
		}

		vector.putAll(cloned);
	}

	/**
	 * 
	 * @param clientID
	 * @return the scalar clock associated with the passed client ID
	 */
	public Integer getScalar(UUID clientID) {
		Integer value = vector.get(clientID);
		return value;
	}

	@Override
	public String toString() {
		String returnString = "{\n";

		Map<UUID, Integer> cloned = new HashMap<UUID, Integer>(vector);
		Iterator<UUID> iterator = cloned.keySet().iterator();
		while (iterator.hasNext()) {
			UUID key = iterator.next();
			Integer value = cloned.get(key);
			returnString += "\t\t\t" + key.toString().substring(0, 4) + " =>"
					+ value + "\n";
		}

		return returnString + "}]";
	}

	@Override
	public Iterator<UUID> iterator() {
		Map<UUID, Integer> cloned = new HashMap<UUID, Integer>(vector);
		Iterator<UUID> iterator = cloned.keySet().iterator();
		return iterator;
	}

	@Override
	public int compareTo(VectorClock received) {
		Integer thisScalar = this.getScalar(received.clientID);
		Integer otherScalar = received.getScalar(received.clientID);
		// if i'm new to that client, I suppose this is his first message to
		// me. So i force its receiving.
		if (thisScalar == null) {
			thisScalar = otherScalar - 1;
		}
		if ((thisScalar + 1) == otherScalar) {
			Iterator<UUID> iterator = received.iterator();
			while (iterator.hasNext()) {
				UUID id = iterator.next();
				thisScalar = this.getScalar(id);
				otherScalar = received.getScalar(id);
				// if I do not have information about
				// a client, I suppose everything it's right.
				if (thisScalar == null) {
					thisScalar = otherScalar;
				}
				if (id != received.clientID && otherScalar > thisScalar) {
					return -1;
				}
			}
		} else {
			return -1;
		}
		return 0;
	}

}
