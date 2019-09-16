package com.cogitech.getvy.service.impl;

import com.cogitech.getvy.service.CycleService;
import com.cogitech.getvy.domain.Cycle;
import com.cogitech.getvy.repository.CycleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Cycle}.
 */
@Service
@Transactional
public class CycleServiceImpl implements CycleService {

    private final Logger log = LoggerFactory.getLogger(CycleServiceImpl.class);

    private final CycleRepository cycleRepository;

    public CycleServiceImpl(CycleRepository cycleRepository) {
        this.cycleRepository = cycleRepository;
    }

    /**
     * Save a cycle.
     *
     * @param cycle the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Cycle save(Cycle cycle) {
        log.debug("Request to save Cycle : {}", cycle);
        return cycleRepository.save(cycle);
    }

    /**
     * Get all the cycles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cycle> findAll() {
        log.debug("Request to get all Cycles");
        return cycleRepository.findAll();
    }


    /**
     * Get one cycle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Cycle> findOne(Long id) {
        log.debug("Request to get Cycle : {}", id);
        return cycleRepository.findById(id);
    }

    /**
     * Delete the cycle by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cycle : {}", id);
        cycleRepository.deleteById(id);
    }
}
