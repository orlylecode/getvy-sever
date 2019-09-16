package com.cogitech.getvy.service;

import com.cogitech.getvy.domain.Examen;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Examen}.
 */
public interface ExamenService {

    /**
     * Save a examen.
     *
     * @param examen the entity to save.
     * @return the persisted entity.
     */
    Examen save(Examen examen);

    /**
     * Get all the examen.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Examen> findAll(Pageable pageable);


    /**
     * Get the "id" examen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Examen> findOne(Long id);

    /**
     * Delete the "id" examen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
