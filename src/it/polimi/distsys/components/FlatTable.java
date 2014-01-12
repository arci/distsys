package it.polimi.distsys.components;

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
	private List<Key> zeros;
	private List<Key> ones;
	private Map<UUID, Integer> members;
	private KeyGenerator keygen;

	public FlatTable() {
		zeros = new ArrayList<Key>();
		ones = new ArrayList<Key>();
		members = new HashMap<UUID, Integer>();
		try {
			keygen = KeyGenerator.getInstance(Decrypter.ALGORITHM);
			keygen.init(new SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Key> getKEKs(UUID memberID) throws Exception {
		if (!members.keySet().contains(memberID)) {
			throw new Exception() {
				private static final long serialVersionUID = 4813696989637416032L;

				@Override
				public String getMessage() {
					return "Member unknown";
				}
			};
		}
		Integer ID = members.get(memberID);
		List<Key> keks = new ArrayList<Key>();
		String[] base2 = Integer.toBinaryString(ID).split("(?!^)");
		for (int i = 0; i < base2.length; i++) {
			if (Integer.valueOf(base2[i]) == 1) {
				keks.add(ones.get(i));
			} else {
				keks.add(zeros.get(i));
			}
		}
		return keks;
	}

	public List<Key> updateKEKs(UUID memberID) throws Exception {
		if (!members.keySet().contains(memberID)) {
			throw new Exception() {
				private static final long serialVersionUID = -6061049907345352656L;

				@Override
				public String getMessage() {
					return "Member unknown";
				}
			};
		}
		Integer ID = members.get(memberID);
		List<Key> keks = new ArrayList<Key>();
		String[] base2 = Integer.toBinaryString(ID).split("(?!^)");
		for (int i = 0; i < base2.length; i++) {
			if (Integer.valueOf(base2[i]) == 1) {
				ones.remove(i);
				ones.add(i, keygen.generateKey());
				keks.add(ones.get(i));
			} else {
				zeros.remove(i);
				zeros.add(i, keygen.generateKey());
				keks.add(zeros.get(i));
			}
		}
		return keks;
	}

	public void join(UUID memberID) throws Exception {
		Integer id = members.keySet().size();
		members.put(memberID, id);

		Integer size = (int) Math
				.ceil((Math.log(members.keySet().size()) / Math.log(2)));
		while (zeros.size() < size) {
			zeros.add(0, keygen.generateKey());
			ones.add(0, keygen.generateKey());
		}

		if (zeros.size() != ones.size()) {
			throw new Exception() {
				private static final long serialVersionUID = 7423062483366813294L;

				@Override
				public String getMessage() {
					return "Inconsistency in flat table lines";
				}
			};
		}
	}

	public void leave(UUID memberID) throws Exception {
		members.remove(memberID);

		Integer size = (int) Math
				.ceil((Math.log(members.keySet().size()) / Math.log(2)));
		while (zeros.size() > size) {
			zeros.remove(0);
			ones.remove(0);
		}

		if (zeros.size() != ones.size()) {
			throw new Exception() {
				private static final long serialVersionUID = 7423062483366813294L;

				@Override
				public String getMessage() {
					return "Inconsistency in flat table lines";
				}
			};
		}
	}

	@Override
	public String toString() {
		String string = "";

		for (UUID id : members.keySet()) {
			string += "\t" + id.toString().substring(0, 4) + "#"
					+ members.get(id);
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
