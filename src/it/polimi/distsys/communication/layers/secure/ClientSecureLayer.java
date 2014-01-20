package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.components.Decrypter;
import it.polimi.distsys.communication.components.Encrypter;
import it.polimi.distsys.communication.components.FlatTable;
import it.polimi.distsys.communication.components.Printer;
import it.polimi.distsys.communication.messages.ACKMessage;
import it.polimi.distsys.communication.messages.JoinMessage;
import it.polimi.distsys.communication.messages.LeaveMessage;
import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

public class ClientSecureLayer extends SecureLayer {
	private Key[] keks;
	private Key dek;
	private Key privateKey;
	private Key publicKey;
	
	private ClientState init;
	private ClientState stop;
	private ClientState state;

	public ClientSecureLayer() {
		super();
		enc = new Encrypter();
		dec = new Decrypter();

		init = new InitState(this);
		stop = new STOPState(this);
		state = init;

		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			KeyPair kp = kpg.generateKeyPair();
			privateKey = kp.getPrivate();
			publicKey = kp.getPublic();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Message> processOnSend(Message msg) {
		if (state.send(msg)) {
			return super.processOnSend(msg);
		}
		return new ArrayList<Message>();
	}

	@Override
	public List<Message> processOnReceive(Message msg) throws IOException {
		if (state.receive(msg)) {
			return super.processOnReceive(msg);
		}
		return new ArrayList<Message>();
	}

	@Override
	public void join(UUID memberID, Key publicKey) {}

	@Override
	public void join() throws IOException {
		underneath.join();
		sendDown(new JoinMessage(Peer.ID, getPublic()));
	}

	@Override
	public void leave(UUID memberID) {
	}

	@Override
	public void updateDEK(SealedObject dek) {
		if(keks == null){
			return;
		}
		Printer.printDebug(getClass(), "updating DEK");
		try {
			Printer.printDebug(getClass(), "starting decryption with old DEK");
			this.dek = (Key) dec.decrypt(dek, this.dek);
			Printer.printDebug(getClass(), "it was encrypted with old DEK");
			enc.setKey(this.dek);
			dec.setKey(this.dek);

			Printer.printDebug(getClass(), "DEK updated");
			return;
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | ClassNotFoundException
				| IllegalBlockSizeException | BadPaddingException | IOException e1) {
			Printer.printDebug(getClass(), "decryption with old DEK failed");
		}

		Printer.printDebug(getClass(), "starting decryption with old KEKs");

		for (Key kek : keks) {
			try {
				this.dek = (Key) dec.decrypt(dek, kek);
				Printer.printDebug(getClass(), "it was encrypted with old KEKs");
				break;
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchPaddingException | ClassNotFoundException
					| IllegalBlockSizeException | BadPaddingException
					| IOException e) {
				// this means the KEK is not appropriate for decryption
			}
		}
		enc.setKey(this.dek);
		dec.setKey(this.dek);

		Printer.printDebug(getClass(), "DEK updated");
	}

	@Override
	public void updateKEK(SealedObject kek) {
		if(keks == null){
			return;
		}
		Printer.printDebug(getClass(), "updating KEK");
		Object o = null;
		try {
			Printer.printDebug(getClass(),
					"starting level1 decryption with DEK");
			o = dec.decrypt(kek, this.dek);
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | ClassNotFoundException
				| IllegalBlockSizeException | BadPaddingException | IOException e1) {
			Printer.printDebug(getClass(), "level1 decryption failed");
			o = kek;
		}

		Printer.printDebug(getClass(), "starting level2 decryption with KEKs");

		for (int i = 0; i < keks.length; i++) {
			try {
				keks[i] = (Key) dec.decrypt(o, keks[i]);
				Printer.printDebug(getClass(), "it is a level2 decryption");
				Printer.printDebug(getClass(), "KEK" + i + " updated");
				break;
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchPaddingException | ClassNotFoundException
					| IllegalBlockSizeException | BadPaddingException
					| IOException e) {
				Printer.printDebug(getClass(), "level2 decryption failed");
			}
		}
	}

	public void init(SealedObject[] keks, SealedObject dek) {
		try {
			this.keks = new Key[FlatTable.BITS];
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, getPrivate());
			for (int i = 0; i < keks.length; i++) {
				this.keks[i] = (Key) keks[i].getObject(cipher);
			}
			
			this.dek = (Key) dek.getObject(cipher);
			dec.setKey(this.dek);
			enc.setKey(this.dek);
			Printer.printDebug(getClass(), "init completed");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException | ClassNotFoundException
				| IllegalBlockSizeException | BadPaddingException | IOException e) {
			Printer.printDebug(getClass(), "the init was not for me");
		}
	}

	@Override
	public void leave() throws IOException {
		sendDown(new LeaveMessage(Peer.ID));
	}

	public void sendACK() throws IOException {
		sendDown(new ACKMessage(Peer.ID));
	}

	public Key getKEK(int index) {
		return keks[index];
	}

	public void setState(ClientState state) {
		Printer.printDebug(getClass(), "\t\t\t\tSTATE SET TO "
				+ state.getClass().getSimpleName().toUpperCase());
		this.state = state;
	}

	public void keysReceived() throws IOException {
		state.keysReceived();
		for (Message m : init.getMessages()) {
			send(m);
		}
	}

	public void stop() throws IOException {
		state.stop();
	}

	public void done() throws IOException {
		state.done();
		for (Message m : stop.getMessages()) {
			send(m);
		}
	}

	public Key getPublic() {
		return publicKey;
	}

	public Key getPrivate() {
		return privateKey;
	}
}
