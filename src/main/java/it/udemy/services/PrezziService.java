package it.udemy.services;

import it.udemy.dtos.ArticoliDto;

public interface PrezziService {
	
	double selPrezzoArt(String CodArt, String IdList);
	
	void insPrezzoListino(ArticoliDto articolo);
}