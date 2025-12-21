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
	
	@Size(min = 5, max = 20, message = "Il codice articolo deve essere compreso fra 5 e 20 caratteri")
	@NotNull(message = "Non è possibile inserire un valore null nel codice articolo")
	private String codart;
	
	@Size(min = 6, max = 80, message = "La descrizione dell'articolo deve essere compresa fra 6 e 80 caratteri")
	private String descrizione;
	
	@NotBlank(message = "Inserire l'unità di misura dell'articolo")
	private String um;
	
	private String codstat;
	
	@Max(value = 99, message = "Il valore massimo dei pezzi per cartone è 99")
	@Min(value = 1, message = "Il valore minimo dei pezzi per cartone è 1")
	@NotNull(message = "E' necessario inserire un valore dei pezzi per cartone")
	private int pzcart;
	
	@Max(value = 999, message = "Il valore massimo del peso è 999")
	@DecimalMin(value = "0.01", message = "Il valore minimo del peso è 0.01")
	@NotNull(message = "E' necessario inserire il valore del peso dell'articolo")
	private double peso;
	
	private String status;
	private Date data;
	
	@Max(value = 1000, message = "Il valore massimo del prezzo è 1000")
	@DecimalMin(value = "0.01", message = "Il valore minimo del prezzo è 0.01")
	@NotNull(message = "E' necessario inserire il valore del prezzo dell'articolo")
	private double prezzo = 0;
	
	@NotNull(message = "E'necessario selezionare l'iva dell'articolo")
	private int idIva;
	
	@NotNull(message = "E'necessario selezionare la categoria dell'articolo")
	private int idCat;
	
	@NotNull(message = "E' necessario selezionare lo stato dell'articolo")
	@NotBlank(message = "E' necessario selezionare lo stato dell'articolo")
	private String idStatoArt;
	
	private Set<BarcodeDto> barcode = new HashSet<>();
	private IngredientiDto ingredienti;
	private CategoriaDto famAssort;
	private IvaDto iva;
}