package com.cogitech.getvy.service;

import com.cogitech.getvy.domain.JourDeGarde;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link JourDeGarde}.
 */
public interface JourDeGardeService {

    /**
     * Save a jourDeGarde.
     *
     * @param jourDeGarde the entity to save.
     * @return the persisted entity.
     */
    JourDeGarde save(JourDeGarde jourDeGarde);

    /**
     * Get all the jourDeGardes.
     *
     * @return the list of entities.
     */
    List<JourDeGarde> findAll();


    /**
     * Get the "id" jourDeGarde.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JourDeGarde> findOne(Long id);

    /**
     * Delete the "id" jourDeGarde.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
