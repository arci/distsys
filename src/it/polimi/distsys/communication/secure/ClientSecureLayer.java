package it.polimi.distsys.communication.secure;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.chat.Printer;
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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

public class ClientSecureLayer extends SecureLayer {
	public static boolean DEBUG;
	private Key[] keks;
	private Key dek;
	private Key privateKey;
	private Key publicKey;
	private ClientState state;

	public ClientSecureLayer() {
		super();
		enc = new Encrypter();
		dec = new Decrypter();
		state = new InitState(this);

		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			KeyPair kp = kpg.generateKeyPair();
			privateKey = kp.getPrivate();
			publicKey = kp.getPublic();
		} catch (NoSuchAlgorithmException e) {
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
	public void join() throws IOException {
		underneath.join();
		sendDown(new JoinMessage(Peer.ID, getPublic()));
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
			e.printStackTrace();
		}
	}

	public void updateOnJoin(SealedObject[] keks, SealedObject dek) {
		if (this.keks == null) {
			return;
		}
		try {
			Cipher cipher = Cipher.getInstance(Decrypter.ALGORITHM);
			for (int i = 0; i < keks.length; i++) {
				cipher.init(Cipher.DECRYPT_MODE, this.keks[i]);
				try {
					this.keks[i] = (Key) keks[i].getObject(cipher);
					Printer.printDebug(getClass(), "KEK " + i + " updated");
				} catch (ClassNotFoundException | IllegalBlockSizeException
						| BadPaddingException | IOException e) {
					Printer.printDebug(getClass(), "KEK " + i + " NOT updated");
				}
			}

			cipher.init(Cipher.DECRYPT_MODE, this.dek);
			try {
				this.dek = (Key) dek.getObject(cipher);
			} catch (ClassNotFoundException | IllegalBlockSizeException
					| BadPaddingException | IOException e) {
				e.printStackTrace();
			}
			dec.setKey(this.dek);
			enc.setKey(this.dek);
			Printer.printDebug(getClass(), "DEK updated");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	
	public void updateOnLeave(SealedObject[] keks, SealedObject[] deks) {
		if (this.keks == null) {
			return;
		}
		try {
			Cipher kekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
			Cipher dekCipher = Cipher.getInstance(Decrypter.ALGORITHM);
			dekCipher.init(Cipher.DECRYPT_MODE, this.dek);
			for (int i = 0; i < keks.length; i++) {
				kekCipher.init(Cipher.DECRYPT_MODE, this.keks[i]);
				try {
					SealedObject firstStep = (SealedObject) keks[i].getObject(dekCipher);
					this.keks[i] = (Key)firstStep.getObject(kekCipher);
					Printer.printDebug(getClass(), "KEK " + i + " updated");
				} catch (ClassNotFoundException | IllegalBlockSizeException
						| BadPaddingException | IOException e) {
					Printer.printDebug(getClass(), "KEK " + i + " NOT updated");
				}
			}
			
			for (int i = 0; i < keks.length; i++) {
				kekCipher.init(Cipher.DECRYPT_MODE, this.keks[i]);
				try {
					this.dek = (Key) deks[i].getObject(kekCipher);
					dec.setKey(this.dek);
					enc.setKey(this.dek);
					Printer.printDebug(getClass(), "DEK updated");
					break;
				} catch (ClassNotFoundException | IllegalBlockSizeException
						| BadPaddingException | IOException e) {
					//I was only trying with the wrong key...
				}
			}
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException e) {
			e.printStackTrace();
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
	}

	public void stop() throws IOException {
		state.stop();
	}

	public void done() throws IOException {
		state.done();
	}

	public Key getPublic() {
		return publicKey;
	}

	public Key getPrivate() {
		return privateKey;
	}
}
