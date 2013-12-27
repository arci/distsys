package it.polimi.distsys.communication;

import java.util.ArrayList;
import java.util.List;

public class Deserializer implements Receiver {
	private Receiver receiver;
	public static final String SEPARATOR = "#";

	public Deserializer(Receiver receiver) {
		super();
		this.receiver = receiver;
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
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Message> list = new ArrayList<Message>();
		list.add(received);
		
		return list;
	}

}
