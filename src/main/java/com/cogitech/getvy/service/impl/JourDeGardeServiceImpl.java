package com.cogitech.getvy.service.impl;

import com.cogitech.getvy.service.JourDeGardeService;
import com.cogitech.getvy.domain.JourDeGarde;
import com.cogitech.getvy.repository.JourDeGardeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link JourDeGarde}.
 */
@Service
@Transactional
public class JourDeGardeServiceImpl implements JourDeGardeService {

    private final Logger log = LoggerFactory.getLogger(JourDeGardeServiceImpl.class);

    private final JourDeGardeRepository jourDeGardeRepository;

    public JourDeGardeServiceImpl(JourDeGardeRepository jourDeGardeRepository) {
        this.jourDeGardeRepository = jourDeGardeRepository;
    }

    /**
     * Save a jourDeGarde.
     *
     * @param jourDeGarde the entity to save.
     * @return the persisted entity.
     */
    @Override
    public JourDeGarde save(JourDeGarde jourDeGarde) {
        log.debug("Request to save JourDeGarde : {}", jourDeGarde);
        return jourDeGardeRepository.save(jourDeGarde);
    }

    /**
     * Get all the jourDeGardes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<JourDeGarde> findAll() {
        log.debug("Request to get all JourDeGardes");
        return jourDeGardeRepository.findAll();
    }


    /**
     * Get one jourDeGarde by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<JourDeGarde> findOne(Long id) {
        log.debug("Request to get JourDeGarde : {}", id);
        return jourDeGardeRepository.findById(id);
    }

    /**
     * Delete the jourDeGarde by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JourDeGarde : {}", id);
        jourDeGardeRepository.deleteById(id);
    }
}
