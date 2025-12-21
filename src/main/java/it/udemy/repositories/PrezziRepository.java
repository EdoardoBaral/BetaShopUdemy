package it.udemy.repositories;

import it.udemy.entities.DettListini;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrezziRepository extends JpaRepository<DettListini, Integer> {
	
	//JPQL
	@Query(value = "select b from Listini a join a.dettListini b where b.codArt = :codart and a.id = :idlist")
	DettListini selByCodArtAndList(@Param("codart") String CodArt, @Param("idlist") String Listino);
	
	//SQL
	@Modifying
	@Query(value = "insert into dettlistini (id, codart, prezzo, idlist) values (nextval('dettlistini_seq'), :codArt, :prezzo, :idList)", nativeQuery = true)
	void insertPrezzoListino(@Param("codArt") String codArt, @Param("prezzo") double prezzo, @Param("idList") String idList);
	
	//SQL
	@Modifying
	@Query(value = "delete from dettlistini where codart = :codArt and idlist = :idList", nativeQuery = true)
	void deletePrezzoListino(@Param("codArt") String codArt, @Param("idList") String idList);
}