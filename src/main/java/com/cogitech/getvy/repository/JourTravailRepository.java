package com.cogitech.getvy.repository;
import com.cogitech.getvy.domain.JourTravail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JourTravail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JourTravailRepository extends JpaRepository<JourTravail, Long> {

}
