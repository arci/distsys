package it.polimi.distsys.components;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class VectorClock implements Serializable {
	private static final long serialVersionUID = 1019945114852671821L;
	private Map<UUID, Integer> vector;
	private UUID clientID;
	
	public VectorClock(UUID clientID) {
		super();
		this.clientID = clientID;
		vector = new HashMap<UUID, Integer>();
		vector.put(clientID, 0);
		
	}


	/**
	 * update number of client in the corresponding position
	 */
	public synchronized void increment() {
		int value = vector.get(clientID);
		vector.put(clientID, value++);
	}
	
	
	/**
	 * 
	 * @param v
	 * merge the received vector clock with the local one
	 * 
	 */
	public synchronized void merge(VectorClock v){
		Iterator<UUID> iterator = vector.keySet().iterator();
		while(iterator.hasNext()){
			UUID key = iterator.next();
			Integer value = vector.get(key);
			Integer otherValue = v.getScalar(key);
			
			if(value < otherValue){
				vector.put(key, otherValue);
			}
		}
	}
	
	/**
	 * 
	 * @param clientID
	 * @return the scalar clock associated with the passed client ID
	 */
	public synchronized int getScalar(UUID clientID){
		Integer value = vector.get(clientID);
		if(value == null){
			value = 0;
		}
		return value;
	}


	@Override
	public String toString() {
		String returnString = "VectorClock [clientID=" + clientID +", vector= " ;
		Iterator<UUID> iterator = vector.keySet().iterator();
		while(iterator.hasNext()){
			returnString += vector.get(iterator.next()) + ",";
		}
		return returnString  + "]";
	}
	
}
