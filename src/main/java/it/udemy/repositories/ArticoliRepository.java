package it.udemy.repositories;

import it.udemy.entities.Articoli;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticoliRepository extends JpaRepository<Articoli, String> {
	
	//Query Method
	Articoli findByCodArt(String codArt);
	
	//Query Method
	List<Articoli> findByDescrizioneLike(String descrizione, Pageable pageable);
	
	//Query Method
	List<Articoli> findByCodStatOrderByDescrizione(String codStat);
	
	//JPQL
	@Query(value="select a from Articoli a join a.barcode b where b.barcode in (:ean)")
	Articoli selByEan(@Param("ean") String ean);
	
	//SQL
	@Query(value = "select count(*) from ARTICOLI where DESCRIZIONE like :desArt", nativeQuery = true)
	int countRecords(@Param("desArt") String descrizione);
}