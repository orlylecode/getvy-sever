package com.cogitech.getvy.service;

import com.cogitech.getvy.domain.Cycle;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Cycle}.
 */
public interface CycleService {

    /**
     * Save a cycle.
     *
     * @param cycle the entity to save.
     * @return the persisted entity.
     */
    Cycle save(Cycle cycle);

    /**
     * Get all the cycles.
     *
     * @return the list of entities.
     */
    List<Cycle> findAll();


    /**
     * Get the "id" cycle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cycle> findOne(Long id);

    /**
     * Delete the "id" cycle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
