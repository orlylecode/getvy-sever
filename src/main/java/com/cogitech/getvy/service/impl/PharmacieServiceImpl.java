package com.cogitech.getvy.service.impl;

import com.cogitech.getvy.service.PharmacieService;
import com.cogitech.getvy.domain.Pharmacie;
import com.cogitech.getvy.repository.PharmacieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Pharmacie}.
 */
@Service
@Transactional
public class PharmacieServiceImpl implements PharmacieService {

    private final Logger log = LoggerFactory.getLogger(PharmacieServiceImpl.class);

    private final PharmacieRepository pharmacieRepository;

    public PharmacieServiceImpl(PharmacieRepository pharmacieRepository) {
        this.pharmacieRepository = pharmacieRepository;
    }

    /**
     * Save a pharmacie.
     *
     * @param pharmacie the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Pharmacie save(Pharmacie pharmacie) {
        log.debug("Request to save Pharmacie : {}", pharmacie);
        return pharmacieRepository.save(pharmacie);
    }

    /**
     * Get all the pharmacies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Pharmacie> findAll(Pageable pageable) {
        log.debug("Request to get all Pharmacies");
        return pharmacieRepository.findAll(pageable);
    }


    /**
     * Get one pharmacie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pharmacie> findOne(Long id) {
        log.debug("Request to get Pharmacie : {}", id);
        return pharmacieRepository.findById(id);
    }

    /**
     * Delete the pharmacie by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pharmacie : {}", id);
        pharmacieRepository.deleteById(id);
    }
}
