package it.udemy.services;

import it.udemy.dtos.ArticoliDto;
import it.udemy.entities.DettListini;
import it.udemy.repositories.PrezziRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrezziServiceImpl implements PrezziService {
	
	private final PrezziRepository prezziRepository;
	
	@Override
	public double selPrezzoArt(String CodArt, String IdList) {
		DettListini dettListini = prezziRepository.selByCodArtAndList(CodArt, IdList);
		if(dettListini != null) {
			return dettListini.getPrezzo();
		} else {
			return 0;
		}
	}
	
	@Override
	@Transactional
	public void insPrezzoListino(ArticoliDto articolo) {
		String idListino = "1";
		prezziRepository.deletePrezzoListino(articolo.getCodart(), idListino); //eliminiamo il vecchio prezzo
		prezziRepository.insertPrezzoListino(articolo.getCodart(), articolo.getPrezzo(), idListino); //inseriamo il nuovo prezzo
	}
}