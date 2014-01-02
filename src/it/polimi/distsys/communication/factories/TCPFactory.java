package it.polimi.distsys.communication.factories;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.TCPLayer;

import java.io.InputStream;
import java.io.OutputStream;

public class TCPFactory {
	public Layer make(InputStream in, OutputStream out) {
		return new TCPLayer(in, out, null);
	}
}
