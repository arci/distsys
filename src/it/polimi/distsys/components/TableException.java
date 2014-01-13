package it.polimi.distsys.components;

public class TableException extends Exception{
	private static final long serialVersionUID = 7550859866569271640L;
	String message;
	
	public TableException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
