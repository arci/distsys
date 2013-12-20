package it.polimi.distsys.communication;

public interface Message {

    public void display();

    public Message unpack();
}
