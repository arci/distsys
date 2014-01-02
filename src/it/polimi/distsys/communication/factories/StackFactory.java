package it.polimi.distsys.communication.factories;

import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.Stack;
import it.polimi.distsys.communication.TCPLayer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StackFactory {
	public static Stack makeTCPStack(InputStream in, OutputStream out) {
		List<Layer> layers = new ArrayList<Layer>(Arrays.asList(new TCPLayer(in, out)));
		return new Stack(layers);
	}
}
