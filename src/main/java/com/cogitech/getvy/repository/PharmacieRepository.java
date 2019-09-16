package com.cogitech.getvy.repository;
import com.cogitech.getvy.domain.Pharmacie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pharmacie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PharmacieRepository extends JpaRepository<Pharmacie, Long> {

}
