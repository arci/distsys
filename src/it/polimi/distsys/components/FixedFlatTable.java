package it.polimi.distsys.components;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.crypto.KeyGenerator;

public class FixedFlatTable {
	public final static int MAX_GROUP_SIZE = 8;
	public final static int BITS = (int) Math.ceil(Math.log(MAX_GROUP_SIZE)/Math.log(2));
	private Key[] zeros;
	private Key[] ones;
	private List<UUID> members;
	private KeyGenerator keygen;
	private Key dek;
	
	public FixedFlatTable() {
		zeros = new Key[BITS];
		ones = new Key[BITS];
		members = new ArrayList<UUID>();
		try {
			keygen = KeyGenerator.getInstance(Decrypter.ALGORITHM);
			keygen.init(new SecureRandom());
			dek = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Key> getKEKs(UUID memberID) throws TableException {
		List<Key> keks = new ArrayList<Key>();
		int[] bits = getBits(memberID);
		for (int i = 0; i < bits.length; i++) {
			if (Integer.valueOf(bits[i]) == 1) {
				keks.add(ones[i]);
			} else {
				keks.add(zeros[i]);
			}
		}
		return keks;
	}

	public List<Key> updateKEKs(UUID memberID) throws Exception {
		List<Key> keks = new ArrayList<Key>();
		int[] bits = getBits(memberID);
		for (int i = 0; i < bits.length; i++) {
			if (Integer.valueOf(bits[i]) == 1) {
				ones[i] = keygen.generateKey();
				keks.add(ones[i]);
			} else {
				zeros[i] = keygen.generateKey();
				keks.add(zeros[i]);
			}
		}
		return keks;
	}

	public void join(UUID memberID) throws TableException {
		if(members.size() + 1 > MAX_GROUP_SIZE){
			throw new TableException("Group size exceeded!");
		}
		members.add(memberID);
	}

	public void leave(UUID memberID) throws Exception {
		members.remove(memberID);
	}

	public Key refreshDEK() {
		dek = keygen.generateKey();
		return dek;
	}
	
	public Key getDEK() {
		return dek;
	}
	
	private int[] getBits(UUID memberID) throws TableException{
		if (!members.contains(memberID)) {
			throw new TableException("The given UUID isn't in members!");
		}
		Integer ID = members.indexOf(memberID);
		String base2 = Integer.toBinaryString(ID);
		while(base2.length() < BITS){
			base2 = "0" + base2;
		}
		
		String[] splitted = base2.split("(?!^)");
		int[] toReturn = new int[splitted.length];
		
		for(int i=0; i< toReturn.length; i++){
			toReturn[i] = Integer.valueOf(splitted[i]);
		}
		
		return toReturn;
	}

	@Override
	public String toString() {
		String string = "";

		for (UUID id : members) {
			string += "\t" + id.toString().substring(0, 4) + "#"
					+ members.indexOf(id);
		}
		string += "\n0:";
		for (Key k : zeros) {
			string += "\t" + k.toString().substring(0, 4);
		}
		string += "\n1:";
		for (Key k : ones) {
			string += "\t" + k.toString().substring(0, 4);
		}
		return string;
	}

}
