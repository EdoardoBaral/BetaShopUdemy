package it.udemy.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {
	
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = "password";
		
		System.out.println("Password originale: " + password);
		System.out.println("\nPassword cifrata (BCrypt) - 3 cifrature diverse:");
		String hash1 = encoder.encode(password);
		String hash2 = encoder.encode(password);
		String hash3 = encoder.encode(password);
		System.out.println("1. " + hash1);
		System.out.println("2. " + hash2);
		System.out.println("3. " + hash3);
		
		System.out.println("\n--- Verifica con il primo hash ---");
		System.out.println("Hash salvato nel DB: " + hash1);
		System.out.println("Password inserita al login: password");
		System.out.println("Corrisponde? " + encoder.matches(password, hash1));
		
		System.out.println("\n--- Verifica con il secondo hash ---");
		System.out.println("Hash salvato nel DB: " + hash2);
		System.out.println("Password inserita al login: password");
		System.out.println("Corrisponde? " + encoder.matches(password, hash2));
		
		System.out.println("\n--- Verifica con password sbagliata ---");
		System.out.println("Hash salvato nel DB: " + hash1);
		System.out.println("Password inserita al login: passwordSbagliata");
		System.out.println("Corrisponde? " + encoder.matches("passwordSbagliata", hash1));
	}
}
