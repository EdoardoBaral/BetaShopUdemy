package it.udemy.services;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {
	
	public boolean auth(String username, String password) {
		boolean isValidUserName = username.equalsIgnoreCase("postgres");
		boolean isValidPassword = password.equalsIgnoreCase("password");
		
		return isValidUserName && isValidPassword;
	}
}