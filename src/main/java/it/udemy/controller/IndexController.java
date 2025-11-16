package it.udemy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	private static final String SALUTI = "Saluti, sono la tua prima applicazione web creata in SpringBoot 3";
	
	@GetMapping(value = "/")
	public String getWelcome(Model model) {
		model.addAttribute("intestazione", "Benvenuti nella root page di BetaShopUdemy");
		model.addAttribute("saluti", SALUTI);
		return "index";
	}
}
