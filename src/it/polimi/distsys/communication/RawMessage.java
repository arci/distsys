package it.polimi.distsys.communication;

public class RawMessage implements Message {
	private String content;

	public RawMessage(String string) {
		content = string;
	}

	@Override
	public void display() {
		//does nothing
	}

	@Override
	public Message unpack() {
		return this;
	}

	public String getContent() {
		return content;
	}

}
