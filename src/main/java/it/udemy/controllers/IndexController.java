package it.udemy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	
	private static final String SALUTI = "Saluti, sono la tua prima applicazione web creata in SpringBoot 3 e Thymeleaf";
	
	@GetMapping(value = "/")
	public String getWelcome(Model model) {
		model.addAttribute("intestazione", "Benvenuti nella root page di BetaShopUdemy");
		model.addAttribute("saluti", SALUTI);
		
		return "index";
	}
	
	@GetMapping(value="index")
	public String getWelcome2(Model model,  @RequestParam("name") String name) {
		model.addAttribute("intestazione", String.format("Benvenuto %s nella index page della webapp BetaShopUdemy", name));
		model.addAttribute("saluti", SALUTI);
		
		return "index";
	}
}
