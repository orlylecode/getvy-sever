package com.cogitech.getvy.service;

import com.cogitech.getvy.domain.Pharmacie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Pharmacie}.
 */
public interface PharmacieService {

    /**
     * Save a pharmacie.
     *
     * @param pharmacie the entity to save.
     * @return the persisted entity.
     */
    Pharmacie save(Pharmacie pharmacie);

    /**
     * Get all the pharmacies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Pharmacie> findAll(Pageable pageable);


    /**
     * Get the "id" pharmacie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pharmacie> findOne(Long id);

    /**
     * Delete the "id" pharmacie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
