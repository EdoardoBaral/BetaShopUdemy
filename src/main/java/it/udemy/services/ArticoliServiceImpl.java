package it.udemy.services;

import it.udemy.dtos.ArticoliDto;
import it.udemy.entities.Articoli;
import it.udemy.entities.FamAssort;
import it.udemy.entities.Iva;
import it.udemy.repositories.ArticoliRepository;
import it.udemy.repositories.CategoriaRepository;
import it.udemy.repositories.IvaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticoliServiceImpl implements ArticoliService {
	
	private final ArticoliRepository articoliRepository;
	private final PrezziService prezziService;
	private final IvaRepository ivaRepository;
	private final CategoriaRepository categoriaRepository;
	private final ModelMapper modelMapper;
	
	@Override
	@Transactional
	public void insArticolo(ArticoliDto articoloDto) {
		Articoli retVal = articoliRepository.save(convertToArticoli(articoloDto));
		prezziService.insPrezzoListino(articoloDto);
	}
	
	private Articoli convertToArticoli(ArticoliDto articoloDto) {
		articoloDto.setDescrizione(articoloDto.getDescrizione().toUpperCase());
		return modelMapper.map(articoloDto, Articoli.class);
	}
	
	@Override
	public List<ArticoliDto> selAll() {
		return this.convertToDto(articoliRepository.findAll());
	}
	
	@Override
	public ArticoliDto selByCodArt(String codart) {
		return this.convertToDto(articoliRepository.findByCodArt(codart));
	}
	
	@Override
	public List<ArticoliDto> selByDescrizione(String filter, int page, int numrec) {
		filter = "%".concat(filter.toUpperCase().concat("%"));
		Pageable pageAndRecords = PageRequest.of(page, numrec);
		
		return this.convertToDto(articoliRepository.findByDescrizioneLike(filter, pageAndRecords));
	}
	
	@Override
	public ArticoliDto selByBarcode(String barcode) {
		return this.convertToDto(articoliRepository.selByEan(barcode));
	}
	
	@Override
	public List<ArticoliDto> searchArticoli(String filter, int page, int numrec) {
		ArticoliDto articolo = selByCodArt(filter);
		if(articolo == null) {
			articolo = this.selByBarcode(filter);
		}
		
		List<ArticoliDto> articoli = new ArrayList<>();
		if(articolo != null) {
			articoli.add(articolo);
		} else {
			articoli = this.selByDescrizione(filter, page, numrec);
		}
		
		return articoli;
	}
	
	@Override
	public int numRecords(String filter) {
		filter = "%".concat(filter.toUpperCase().concat("%"));
		return articoliRepository.countRecords(filter);
	}
	
	private ArticoliDto convertToDto(Articoli articoli) {
		ArticoliDto articoliDto = null;
		if(articoli != null) {
			articoliDto =  modelMapper.map(articoli, ArticoliDto.class);
			articoliDto.setPrezzo(prezziService.selPrezzoArt(articoliDto.getCodart(), "1"));
		}
		
		return articoliDto;
	}
	
	@Override
	public List<Iva> selAllIva() {
		return ivaRepository.findAll();
	}
	
	@Override
	public List<FamAssort> selAllCat() {
		return categoriaRepository.findAll();
	}
	
	private List<ArticoliDto> convertToDto(List<Articoli> articoli) {
		List<ArticoliDto> articoliDto = articoli.stream()
												.map(source -> modelMapper.map(source, ArticoliDto.class))
												.collect(Collectors.toList());
		articoliDto.forEach(e -> e.setPrezzo(prezziService.selPrezzoArt(e.getCodart(),"1")));
		
		return articoliDto;
	}
	
	@Override
	public void delArticolo(String codart) {
		articoliRepository.delete(articoliRepository.findByCodArt(codart));
	}
}