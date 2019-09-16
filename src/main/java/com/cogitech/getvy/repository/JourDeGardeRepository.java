package com.cogitech.getvy.repository;
import com.cogitech.getvy.domain.JourDeGarde;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JourDeGarde entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JourDeGardeRepository extends JpaRepository<JourDeGarde, Long> {

}
