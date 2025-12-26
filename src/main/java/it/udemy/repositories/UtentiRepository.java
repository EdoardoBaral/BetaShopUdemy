package it.udemy.repositories;

import it.udemy.entities.Utenti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtentiRepository extends JpaRepository<Utenti, String> {
	
	Utenti findByUserid(String userId);
}
