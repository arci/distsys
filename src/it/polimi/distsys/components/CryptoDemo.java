package it.polimi.distsys.components;

public class CryptoDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FlatTable table = new FlatTable();
		Decrypter decrypter = new Decrypter(table.getDEK());
		Encrypter encrypter = new Encrypter(table.getDEK());
		
		String string = args[0];
		
		byte[] enc = encrypter.encrypt(string);
		String dec = decrypter.decrypt(enc);
		
		System.out.println("You entered " + string);
		System.out.println("It was encrypted into " + enc);
		System.out.println("It was decrypted as " + dec);
	}

}
