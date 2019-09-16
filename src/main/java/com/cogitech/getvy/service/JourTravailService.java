package com.cogitech.getvy.service;

import com.cogitech.getvy.domain.JourTravail;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link JourTravail}.
 */
public interface JourTravailService {

    /**
     * Save a jourTravail.
     *
     * @param jourTravail the entity to save.
     * @return the persisted entity.
     */
    JourTravail save(JourTravail jourTravail);

    /**
     * Get all the jourTravails.
     *
     * @return the list of entities.
     */
    List<JourTravail> findAll();


    /**
     * Get the "id" jourTravail.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JourTravail> findOne(Long id);

    /**
     * Delete the "id" jourTravail.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
