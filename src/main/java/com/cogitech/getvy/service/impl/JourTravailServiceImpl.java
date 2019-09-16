package com.cogitech.getvy.service.impl;

import com.cogitech.getvy.service.JourTravailService;
import com.cogitech.getvy.domain.JourTravail;
import com.cogitech.getvy.repository.JourTravailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link JourTravail}.
 */
@Service
@Transactional
public class JourTravailServiceImpl implements JourTravailService {

    private final Logger log = LoggerFactory.getLogger(JourTravailServiceImpl.class);

    private final JourTravailRepository jourTravailRepository;

    public JourTravailServiceImpl(JourTravailRepository jourTravailRepository) {
        this.jourTravailRepository = jourTravailRepository;
    }

    /**
     * Save a jourTravail.
     *
     * @param jourTravail the entity to save.
     * @return the persisted entity.
     */
    @Override
    public JourTravail save(JourTravail jourTravail) {
        log.debug("Request to save JourTravail : {}", jourTravail);
        return jourTravailRepository.save(jourTravail);
    }

    /**
     * Get all the jourTravails.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<JourTravail> findAll() {
        log.debug("Request to get all JourTravails");
        return jourTravailRepository.findAll();
    }


    /**
     * Get one jourTravail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<JourTravail> findOne(Long id) {
        log.debug("Request to get JourTravail : {}", id);
        return jourTravailRepository.findById(id);
    }

    /**
     * Delete the jourTravail by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JourTravail : {}", id);
        jourTravailRepository.deleteById(id);
    }
}
