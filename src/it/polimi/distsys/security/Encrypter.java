package it.polimi.distsys.security;

import it.polimi.distsys.communication.Message;

public class Encrypter {
	public void updateKey(Key newKey) {
		// TODO implement

	}
	
	public byte[] encrypt(Message message){
		//TODO implement
		return message.toString().getBytes();
	}

}
