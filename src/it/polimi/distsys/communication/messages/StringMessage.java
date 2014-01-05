package it.polimi.distsys.communication.messages;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.components.Printer;

public class StringMessage implements Message {
	private static final long serialVersionUID = 9052245167443004983L;
	private String content;

	public StringMessage(String content) {
		super();
		this.content = content;
	}

	@Override
	public void display() {
		Printer.print(content);
	}

	@Override
	public String toString() {
		return content;
	}

	@Override
	public Message unpack() {
		return this;
	}

	@Override
	public void onReceive(Layer layer) {
		// TODO Auto-generated method stub
		
	}

}
