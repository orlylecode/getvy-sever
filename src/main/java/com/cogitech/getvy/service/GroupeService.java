package com.cogitech.getvy.service;

import com.cogitech.getvy.domain.Groupe;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Groupe}.
 */
public interface GroupeService {

    /**
     * Save a groupe.
     *
     * @param groupe the entity to save.
     * @return the persisted entity.
     */
    Groupe save(Groupe groupe);

    /**
     * Get all the groupes.
     *
     * @return the list of entities.
     */
    List<Groupe> findAll();


    /**
     * Get the "id" groupe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Groupe> findOne(Long id);

    /**
     * Delete the "id" groupe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
