package it.udemy.services;

import it.udemy.dtos.ArticoliDto;
import it.udemy.entities.FamAssort;
import it.udemy.entities.Iva;

import java.util.List;

public interface ArticoliService {
	
	List<ArticoliDto> selAll();
	
	ArticoliDto selByCodArt(String codart);
	
	List<ArticoliDto> selByDescrizione(String filter, int page, int numrec);
	
	ArticoliDto selByBarcode(String barcode);
	
	int numRecords(String filter);
	
	List<ArticoliDto> searchArticoli(String filter, int page, int numrec);
	
	List<Iva> selAllIva();
	
	List<FamAssort> selAllCat();
	
	void insArticolo(ArticoliDto articolo);
	
	void delArticolo(String codart);
}