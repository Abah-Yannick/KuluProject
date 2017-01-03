package com.abahyannick.services;

import com.abahyannick.utils.BCrypt;

public final class PasswordService {
	
	
	public static String hashPassword(String plaintext) {
		return BCrypt.hashpw(plaintext, BCrypt.gensalt());
	}
	
	public static boolean checkPassword(String password , String hashedPassword) {
		return BCrypt.checkpw(password, hashedPassword);  
	}
}
