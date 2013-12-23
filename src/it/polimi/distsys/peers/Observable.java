package it.polimi.distsys.peers;

public interface Observable {

	public void register(Observer o);

	public void unregister(Observer o);
}
