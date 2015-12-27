package com.devdream.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {

	//
	// Global
	private static final String ALGORITHM = "AES";
	private static final String KEY = "#@%$<&M|6a(<x>Y)?r123";
	
	//
	// Attributes
	private Key cryptKey;
	private Cipher cipher;
	
	//
	// Constructors
	public EncryptUtil() {
		cryptKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, cryptKey);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	
	public String encrypt(String text) {
		try {
			byte[] encrypted = cipher.doFinal(text.getBytes());
			return new String(encrypted);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String decrypt(String textEncrypted) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, cryptKey);
			return new String(cipher.doFinal(textEncrypted.getBytes()));
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		EncryptUtil eu = new EncryptUtil();
		String textEncrypt = eu.encrypt("Hello");
		System.out.println( textEncrypt );
		System.out.println( eu.decrypt(textEncrypt));
	}

}
