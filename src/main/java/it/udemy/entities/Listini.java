package it.udemy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Listini
{
	@Id
	private String id;
	
	private String descrizione;
	
	private String obsoleto = "No";
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "listino")
	private Set<DettListini> dettListini = new HashSet<>();
}