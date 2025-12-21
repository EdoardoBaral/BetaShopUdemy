package it.udemy.repositories;

import it.udemy.entities.FamAssort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<FamAssort, Integer> {

}