package it.polimi.distsys.communication.secure;

import it.polimi.distsys.chat.Printer;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.KeyGenerator;

public class FlatTable {
	public final static int MAX_GROUP_SIZE = 8;
	public final static int BITS = (int) Math.ceil(Math.log(MAX_GROUP_SIZE)
			/ Math.log(2));
	private Key[] zeros = new Key[BITS];
	private Key[] ones = new Key[BITS];
	private Key dek;
	private UUID[] members = new UUID[MAX_GROUP_SIZE];
	private Map<UUID, Key> publicKeys = new HashMap<UUID, Key>();
	private KeyGenerator keygen;

	public FlatTable() {
		Printer.printDebug(getClass(),
				"FlatTable initialized: max group size: " + MAX_GROUP_SIZE
						+ ", bits: " + BITS);
		try {
			keygen = KeyGenerator.getInstance(Decrypter.ALGORITHM);
			keygen.init(new SecureRandom());
			dek = keygen.generateKey();

			for (int i = 0; i < BITS; i++) {
				ones[i] = keygen.generateKey();
				zeros[i] = keygen.generateKey();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public Key[] getKEKs(UUID memberID) throws TableException {
		Key[] keks = new Key[BITS];
		int[] bits = getBits(memberID);

		// TODO remove
		String toprint = "";
		for (int i = 0; i < BITS; i++) {
			toprint += bits[i];
		}
		Printer.printDebug(getClass(), "getting KEKs for "
				+ memberID.toString().substring(0, 4) + ":" + toprint);

		for (int i = 0; i < bits.length; i++) {
			if (bits[i] == 1) {
				keks[i] = ones[i];
			} else {
				keks[i] = zeros[i];
			}
		}
		return keks;
	}

	public Key[] getOtherKEKs(UUID memberID) throws TableException {
		Key[] keks = new Key[BITS];
		int[] bits = getBits(memberID);
		// TODO remove
		String toprint = "";
		for (int i = 0; i < BITS; i++) {
			toprint += bits[i];
		}
		Printer.printDebug(getClass(), "getting other KEKs for "
				+ memberID.toString().substring(0, 4) + ":" + toprint);

		for (int i = 0; i < bits.length; i++) {
			if (bits[i] == 1) {
				keks[i] = zeros[i];
			} else {
				keks[i] = ones[i];
			}
		}
		return keks;
	}

	public Key[] updateKEKs(UUID memberID) throws TableException {
		Key[] keks = new Key[BITS];
		int[] bits = getBits(memberID);

		// TODO remove
		String toprint = "";
		for (int i = 0; i < BITS; i++) {
			toprint += bits[i];
		}
		Printer.printDebug(getClass(), "updating KEKs for "
				+ memberID.toString().substring(0, 4) + ":" + toprint);

		for (int i = 0; i < bits.length; i++) {
			if (bits[i] == 1) {
				ones[i] = keygen.generateKey();
				keks[i] = ones[i];
			} else {
				zeros[i] = keygen.generateKey();
				keks[i] = zeros[i];
			}
		}
		return keks;
	}

	public Key[] join(UUID memberID) throws TableException {
		for(int i = 0; i < members.length; i++){
			if(members[i] == null){
				members[i] = memberID;
				return getKEKs(memberID);
			}
		}

		throw new TableException("Group size exceeded!");
	}

	public void leave(UUID memberID) throws TableException {
		int pos = getPos(memberID);
		members[pos] = null;
	}

	public Key refreshDEK() {
		dek = keygen.generateKey();
		return dek;
	}

	public Key getDEK() {
		return dek;
	}

	public void addPublicKey(UUID member, Key publicKey) {
		publicKeys.put(member, publicKey);
	}

	public void removePublicKey(UUID member) {
		publicKeys.remove(member);
	}

	public Key getPublicKey(UUID member) {
		return publicKeys.get(member);
	}
	
	public List<UUID> getMembers(){
		List<UUID> m = new ArrayList<UUID>();
		for(int i = 0; i < members.length; i++){
			if(members[i] != null){
				m.add(members[i]);
			}
		}
		return m;
	}
	
	private int getPos(UUID id) throws TableException{
		for(int i = 0; i < members.length; i++){
			if(members[i] != null && members[i].equals(id)){
				return i;	
			}
		}
		throw new TableException("The given UUID isn't in members!");
	}

	private int[] getBits(UUID memberID) throws TableException {
		int ID = getPos(memberID);
		String base2 = Integer.toBinaryString(ID);
		while (base2.length() < BITS) {
			base2 = "0" + base2;
		}

		char[] splitted = base2.toCharArray();
		int[] toReturn = new int[splitted.length];

		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = Character.getNumericValue(splitted[i]);
		}

		return toReturn;
	}
}
