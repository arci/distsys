package it.polimi.distsys.communication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sun.swing.internal.plaf.synth.resources.synth;

public class VectorClock {
	
	private Map<Integer, Integer> vector;
	private int clientID;
	
	public VectorClock(int clientID) {
		super();
		this.clientID = clientID;
		vector = new HashMap<Integer, Integer>();
		vector.put(clientID, 0);
		
	}


	/**
	 * update number of client in the correspond position
	 */
	public synchronized void increment() {
		int value = vector.get(clientID);
		vector.put(clientID, value++);
	}
	
	
	/**
	 * merge two vector clocks
	 */
	public void merge(VectorClock v){
		Iterator<Integer> iterator = vector.keySet().iterator();
		while(iterator.hasNext()){
			int key = iterator.next();
			Integer value = vector.get(key);
			Integer otherValue = v.getScalar(key);
			
			if(value < otherValue){
				vector.put(key, otherValue);
			}
		}
	}
	
	public int getScalar(int clientID){
		Integer value = vector.get(clientID);
		if(value == null){
			value = 0;
		}
		return value;
	}


	@Override
	public String toString() {
		String returnString = "VectorClock [clientID=" + clientID +", vector= " ;
		Iterator<Integer> iterator = vector.keySet().iterator();
		while(iterator.hasNext()){
			returnString = returnString +","+ vector.get(iterator.next());
		}
		return returnString  + "]";
	}
	
}
