package it.polimi.distsys.communication.multicast;

import it.polimi.distsys.chat.Printer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.messages.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MulticastLayer extends Layer {
	public static boolean DEBUG;
	public final static String ADDRESS = "224.0.0.1";
	public final static int PORT = 1234;
	private InetAddress group;
	private MulticastSocket socket;

	public MulticastLayer() throws UnknownHostException {
		super();
		group = InetAddress.getByName(ADDRESS);
	}

	@Override
	public void join() throws IOException {
		socket = new MulticastSocket(PORT);
		socket.joinGroup(group);
		Printer.printDebug(getClass(), "connected: " + socket.getLocalAddress()
				+ ":" + socket.getLocalPort());
	}

	@Override
	public void leave() throws IOException {
		socket.leaveGroup(group);
	}

	@Override
	public void send(Message msg) throws IOException {
		Printer.printDebug(getClass(), "sending "
				+ msg.getClass().getSimpleName());
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(
				new BufferedOutputStream(bs));
		os.flush();
		os.writeObject(msg);
		os.flush();

		byte[] buffer = bs.toByteArray();
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
				group, PORT);
		socket.send(packet);
		os.close();
	}

	@Override
	public List<Message> receive(List<Message> msgs) throws IOException {
		byte[] buffer = new byte[5000];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);

		ByteArrayInputStream bs = new ByteArrayInputStream(buffer);
		ObjectInputStream os = new ObjectInputStream(
				new BufferedInputStream(bs));
		Message msg = null;
		try {
			msg = (Message) os.readObject();
			Printer.printDebug(getClass(), "received "
					+ msg.getClass().getSimpleName() + " from "
					+ packet.getAddress().toString() + ":" + packet.getPort());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		os.close();
		return sendUp(new ArrayList<Message>(Arrays.asList(msg)));
	}

	@Override
	public List<Message> processOnReceive(Message msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> processOnSend(Message msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
