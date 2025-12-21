package it.udemy.repositories;

import it.udemy.entities.Iva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IvaRepository extends JpaRepository<Iva, Integer> {

}