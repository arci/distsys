package it.polimi.distsys.communication;

import java.net.InetAddress;

public class JoinMessage implements Message {

    private final InetAddress address;
    private final int receptionistPort;
    private String hostName;

    public JoinMessage(InetAddress address, int receptionistPort, String name) {
	this.address = address;
	this.receptionistPort = receptionistPort;
	hostName = name;
    }

    public InetAddress getAddress() {
	return address;
    }

    public int getReceptionistPort() {
	return receptionistPort;
    }

    public String getHostName() {
	return this.hostName;
    }

    @Override
    public void display() {
	// TODO Auto-generated method stub
	System.out.println("display on " + getClass().getCanonicalName());
    }

    @Override
    public Message unpack() {
	return this;
    }

}
