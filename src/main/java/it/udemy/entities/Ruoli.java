package it.udemy.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ruoli {
	
	@Id
	private int id;
	
	private String ruolo;
	
	@ManyToOne
	@JoinColumn(name = "idutente", referencedColumnName = "id")
	private Utenti utente;
}
