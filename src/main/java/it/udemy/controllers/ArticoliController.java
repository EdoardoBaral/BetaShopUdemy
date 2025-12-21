package it.udemy.controllers;

import it.udemy.dtos.ArticoliDto;
import it.udemy.dtos.PagingData;
import it.udemy.entities.FamAssort;
import it.udemy.entities.Iva;
import it.udemy.services.ArticoliService;
import it.udemy.utils.Paging;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log
@Controller
@RequiredArgsConstructor
@RequestMapping("/articoli")
public class ArticoliController {
	
	private final ArticoliService articoliService;
	private final Paging paging;
	
	private List<PagingData> pages = new ArrayList<>();
	
	@ModelAttribute("famAssort")
	public List<FamAssort> getFamAssort() {
		return articoliService.selAllCat();
	}
	
	@ModelAttribute("iva")
	public List<Iva> getIva() {
		return articoliService.selAllIva();
	}
	
	@GetMapping(value = "/modifica/{CodArt}")
	public String updArticoli(ModelMap model,
							  @PathVariable("CodArt") String codart,
							  @ModelAttribute("famAssort") List<FamAssort> famAssort,
							  @ModelAttribute("iva") List<Iva> iva) {
		ArticoliDto articolo =  articoliService.selByCodArt(codart);
		
		model.addAttribute("title", "Modifica Articolo");
		model.addAttribute("famAssort",famAssort);
		model.addAttribute("iva",iva);
		model.addAttribute("isModifica", true);
		model.addAttribute("datiart", articolo);
		
		return "gestart";
	}
	
	@GetMapping(value = "/aggiungi")
	public String insArticoli(ModelMap model,
							  @ModelAttribute("famAssort") List<FamAssort> famAssort,
							  @ModelAttribute("iva") List<Iva> iva) {
		model.addAttribute("datiart", new ArticoliDto());
		model.addAttribute("title", "Creazione Articolo");
		model.addAttribute("iva", iva);
		model.addAttribute("famAssort", famAssort);
		
		return "gestart";
	}
	
	@PostMapping(value = "/savedata")
	public String gestUpdArticoli(@Valid @ModelAttribute("datiart") ArticoliDto articolo,
								  BindingResult result) {
		articolo.setData(new Date());
		
		if(result.hasErrors()) {
			return "gestart";
		}
		
		articoliService.insArticolo(articolo);
		
		return "redirect:/articoli/search?filtro=" + articolo.getCodart();
	}
	
	@GetMapping()
	public String getGestArt() {
		return "articoli";
	}
	
	@GetMapping(value="/cerca/descrizione/{filter}")
	public String getArticoli(@PathVariable("filter") String filter,
							  @MatrixVariable(name="page",defaultValue = "0") String page,
							  @MatrixVariable(name="record",defaultValue = "10") String record,
							  ModelMap model) {
		int pageNum = Integer.parseInt(page);
		int recForPage = Integer.parseInt(record);
		
		List<ArticoliDto> articoli = articoliService.selByDescrizione(filter,pageNum,recForPage);
		model.addAttribute("articoli", articoli);
		
		return "articoli";
	}
	
	@GetMapping(value = "/search")
	public String searchItem(@RequestParam(name="filtro") String filtro,
							 @RequestParam(name="selected", required = false, defaultValue = "10") String selected,
							 ModelMap model) {
		log.info(String.format("Ricerca articoli con filtro %s ", filtro));
		
		int pageNum = 0;
		int recForPage;
		
		try {
			recForPage = Integer.parseInt(selected);
		} catch(NumberFormatException e) {
			recForPage = 10; // Valore predefinito nel caso di errore di parsing
		}
		
		List<ArticoliDto> articoli = articoliService.searchArticoli(filtro, pageNum, recForPage);
		int numArt = articoliService.numRecords(filtro);
		boolean notFound = articoli.isEmpty();
		
		log.info(String.format("Trovati %s articoli", numArt));
		
		pages = paging.setPages(pageNum, numArt);
		
		model.addAttribute("articoli", articoli);
		model.addAttribute("PageNum", pageNum);
		model.addAttribute("RecPage", recForPage);
		model.addAttribute("filtro", filtro);
		model.addAttribute("Pages", pages);
		model.addAttribute("notFound", notFound);
		
		return "articoli";
	}
	
	
	
	// articoli/cerca/parametri;paging=0,10;exfilter=1,15?filtro=Barilla&selected=20
	@GetMapping(value="/cerca/{parametri}")
	public String getArticoliWithPar(@MatrixVariable(pathVar = "parametri") Map<String, List<String>> parametri,
									 @RequestParam(name="filtro") String filtro,
									 @RequestParam(name="selected", required = false, defaultValue = "10")  String selected,
									 ModelMap model) {
		int numArt = 0;
		int pageNum = 0;
		int recForPage = 10;
		int diffPage = 0;
		
		List<String> ParamPaging = parametri.get("paging");
		if(ParamPaging != null) {
			try {
				pageNum = Integer.parseInt(ParamPaging.get(0));
				diffPage = Integer.parseInt(ParamPaging.get(1));
				recForPage = Integer.parseInt(selected);
				
				if(pageNum >= 1) {
					pageNum += diffPage;
				} else {
					pageNum = 1;
				}
			} catch(NumberFormatException ex) {
				diffPage = 0;
				pageNum = 0;
				recForPage = 10;
			}
			
			log.info(String.format("pagina: %s, records %s", pageNum, recForPage));
		}
		
		log.info("Cerco tutti gli articoli con descrizione "+ filtro);
		
		List<ArticoliDto> articoli = articoliService.searchArticoli(filtro, pageNum, recForPage);
		numArt = articoliService.numRecords(filtro);
		
		log.info(String.format("Trovati %s articoli", numArt));
		
		pages = paging.setPages(pageNum, numArt);
		
		model.addAttribute("articoli", articoli);
		model.addAttribute("PageNum", pageNum);
		model.addAttribute("RecPage", recForPage);
		model.addAttribute("filtro", filtro);
		model.addAttribute("Pages", pages);
		
		return "articoli";
	}
	
	@GetMapping(value = "/elimina/{CodArt}")
	public String delArticolo(@PathVariable("CodArt") String codart,
							  ModelMap model) {
		try {
			if(!codart.isEmpty()) {
				articoliService.delArticolo(codart);
			}
		} catch(Exception ex) {
			throw new RuntimeException("Errore eliminazione articolo", ex);
		}
		
		return "redirect:/articoli/search?filtro=" + codart;
	}
}