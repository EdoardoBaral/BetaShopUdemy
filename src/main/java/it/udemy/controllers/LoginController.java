package it.udemy.controllers;

import it.udemy.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
@RequiredArgsConstructor
public class LoginController {
	
	private static final String TITOLO = "Accesso & Autenticazione";
	private static final String SOTTOTITOLO = "Procedi ad inserire la userid e la password";
	private static final String ERRMSG = "Spiacente, la userid o la password sono errati!";
	
	private final AuthenticationService authenticationService;
	
	@GetMapping(value="/login")
	public String getLogin(ModelMap model) {
		model.addAttribute("Titolo", TITOLO);
		model.addAttribute("SottoTitolo", SOTTOTITOLO);
		model.addAttribute("ErrMsg", ERRMSG);
		
		return "login";
	}
	
	@PostMapping(value="/login")
	public String gotoWelcomePage(@RequestParam String name,
								  @RequestParam String password,
								  ModelMap model) {
		if(authenticationService.auth(name, password)) {
			model.put("name", name);
			return "welcome";
		} else {
			return "redirect:/login?error=nologged";
		}
	}
}