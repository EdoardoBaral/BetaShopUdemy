package it.udemy.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ArticoliDto {
	
	@Size(min=5, max=20, message="{Size.Articoli.codArt.Validation}")
	@NotNull(message="{NotNull.Articoli.codArt.Validation}")
	private String codart;
	
	@Size(min=6, max=80, message="{Size.Articoli.descrizione.Validation}")
	private String descrizione;
	
	@NotBlank(message="{NotNull.Articoli.um.Validation}")
	private String um;
	
	private String codstat;
	
	@Max(value=100, message="{Max.Articoli.pzCart.Validation}")
	@Min(value=1, message="{Min.Articoli.pzCart.Validation}")
	@NotNull(message="{NotNull.Articoli.pzCart.Validation}")
	private int pzcart;
	
	@Max(value=999, message="{Max.Articoli.pesoNetto.Validation")
	@DecimalMin(value="0.01", message="{Min.Articoli.pesoNetto.Validation}")
	@NotNull(message="{NotNull.Articoli.pesoNetto.Validation}")
	private double peso;
	
	private String status;
	private Date data;
	
	@Max(value=1000, message="{Max.Articoli.prezzo.Validation}")
	@DecimalMin(value="0.01", message="{DecimalMin.Articoli.prezzo.Validation}")
	@NotNull(message="{NotNull.Articoli.prezzo.Validation}")
	private double prezzo = 0;
	
	@NotNull(message="{NotNull.Articoli.idIva.Validation}")
	private int idIva;
	
	@NotNull(message="{NotNull.Articoli.idCat.Validation}")
	private int idCat;
	
	@NotNull(message="{NotNull.Articoli.idStatoArt.Validation}")
	@NotBlank(message="{NotNull.Articoli.idStatoArt.Validation}")
	private String idStatoArt;
	
	private Set<BarcodeDto> barcode = new HashSet<>();
	private IngredientiDto ingredienti;
	private CategoriaDto famAssort;
	private IvaDto iva;
}