package it.udemy.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {

	@GetMapping
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		session = request.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}
		
		for(Cookie cookie : request.getCookies()) {
			cookie.setMaxAge(0);
		}
		
		ResponseCookie springCookie = ResponseCookie.from("user-id", "")
													.maxAge(0)
													.build();
		response.addHeader(HttpHeaders.SET_COOKIE, springCookie.toString());
		
		return "redirect:/login?logout=true";
	}
}
