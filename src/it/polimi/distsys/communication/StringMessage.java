package it.polimi.distsys.communication;

public class StringMessage implements Message {
	private String content;

	public StringMessage(String content) {
		super();
		this.content = content;
	}
	
	public StringMessage() {}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		System.out.println(getClass().getCanonicalName() + " says: " + content);
	}
	
	@Override
	public String toString() {
		return content;
	}

	@Override
	public Message unpack() {
		return this;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

}
