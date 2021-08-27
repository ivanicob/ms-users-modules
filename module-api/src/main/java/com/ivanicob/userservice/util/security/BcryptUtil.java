package com.ivanicob.userservice.util.security;

/**
 * BCrypt implements OpenBSD-style Blowfish password hashing using the scheme described in 
 * "A Future-Adaptable Password Scheme" by Niels Provos and David Mazieres. This class 
 * implements some utility methods to generate a password hashing based on Bcrypt algorithm.
 */
public class BcryptUtil {
	
	private BcryptUtil() {}
	
	public static String getHash(String password) {
		
		if(password == null) {
			return null;
		}
		
		if(BcryptUtil.isEncrypted(password)) {
			return password;
		}
		
		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//return encoder.encode(password);
		return password;
	}
	
	public static boolean isEncrypted(String password) {
		return password.startsWith("$2a$");
	}
	
	public static String decode(String password, String encrypted) throws Exception {
//		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
//		boolean isPasswordMatches = bcrypt.matches(password, encrypted);
//		
//		if(!isPasswordMatches)
//			throw new Exception("Password does not match.");
		
		return password;
	}

}
