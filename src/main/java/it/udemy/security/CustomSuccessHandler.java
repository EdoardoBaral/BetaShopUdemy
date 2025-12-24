package it.udemy.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		ResponseCookie springCookie = ResponseCookie.from("user-id", authentication.getName())
										  			.httpOnly(true)
										  			.secure(false)
										  			.path("/")
										  			.maxAge(600)
										  			.build();
		response.addHeader(HttpHeaders.SET_COOKIE, springCookie.toString());
		response.sendRedirect("/index");
	}
}
