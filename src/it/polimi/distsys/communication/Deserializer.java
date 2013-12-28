package it.polimi.distsys.communication;

import it.polimi.distsys.peers.Peer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Deserializer implements Receiver {
	private Receiver receiver;
	public static final String SEPARATOR = "#";
	private Peer peer;

	public Deserializer(Peer peer, Receiver receiver) {
		super();
		this.receiver = receiver;
		this.peer = peer;
	}

	@Override
	public List<Message> receive(Message m) {
		RawMessage msg = (RawMessage) m;
		String deserialize = msg.getContent();
		String[] parts = deserialize.split(SEPARATOR);
		String className = parts[0];
		Message received = null;
		try {
			received = (Message) Class.forName(className).newInstance();
			Method[] methods = Class.forName(className).getDeclaredMethods();
			for (int i = 0, j = 1; i < methods.length; i++ ) {
				if (methods[i].getName().startsWith("set")) {
					if (methods[i].getName().equals("setPeer")) {
						methods[i].invoke(received, peer);
					} else {
						// TODO make objects from parts
						methods[i].invoke(received, parts[j]);
						j++;
					}
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Message> list = new ArrayList<Message>();
		list.add(received);

		return list;
	}

}
