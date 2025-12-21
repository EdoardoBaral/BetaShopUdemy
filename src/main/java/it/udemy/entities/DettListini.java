package it.udemy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dettlistini")
public class DettListini {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dettlistini_generator")
	@SequenceGenerator(name = "dettlistini_generator", sequenceName = "dettlistini_seq", allocationSize = 1)
	private int id;
	
	@Column(name = "codart")
	private String codArt;
	
	private double prezzo;
	
	@ManyToOne
	@JoinColumn(name = "idlist", referencedColumnName = "id")
	private Listini listino;
}