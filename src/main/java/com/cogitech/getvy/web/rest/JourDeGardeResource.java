package com.cogitech.getvy.web.rest;

import com.cogitech.getvy.domain.JourDeGarde;
import com.cogitech.getvy.service.JourDeGardeService;
import com.cogitech.getvy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cogitech.getvy.domain.JourDeGarde}.
 */
@RestController
@RequestMapping("/api")
public class JourDeGardeResource {

    private final Logger log = LoggerFactory.getLogger(JourDeGardeResource.class);

    private static final String ENTITY_NAME = "jourDeGarde";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JourDeGardeService jourDeGardeService;

    public JourDeGardeResource(JourDeGardeService jourDeGardeService) {
        this.jourDeGardeService = jourDeGardeService;
    }

    /**
     * {@code POST  /jour-de-gardes} : Create a new jourDeGarde.
     *
     * @param jourDeGarde the jourDeGarde to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jourDeGarde, or with status {@code 400 (Bad Request)} if the jourDeGarde has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jour-de-gardes")
    public ResponseEntity<JourDeGarde> createJourDeGarde(@Valid @RequestBody JourDeGarde jourDeGarde) throws URISyntaxException {
        log.debug("REST request to save JourDeGarde : {}", jourDeGarde);
        if (jourDeGarde.getId() != null) {
            throw new BadRequestAlertException("A new jourDeGarde cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JourDeGarde result = jourDeGardeService.save(jourDeGarde);
        return ResponseEntity.created(new URI("/api/jour-de-gardes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jour-de-gardes} : Updates an existing jourDeGarde.
     *
     * @param jourDeGarde the jourDeGarde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jourDeGarde,
     * or with status {@code 400 (Bad Request)} if the jourDeGarde is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jourDeGarde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jour-de-gardes")
    public ResponseEntity<JourDeGarde> updateJourDeGarde(@Valid @RequestBody JourDeGarde jourDeGarde) throws URISyntaxException {
        log.debug("REST request to update JourDeGarde : {}", jourDeGarde);
        if (jourDeGarde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JourDeGarde result = jourDeGardeService.save(jourDeGarde);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jourDeGarde.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jour-de-gardes} : get all the jourDeGardes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jourDeGardes in body.
     */
    @GetMapping("/jour-de-gardes")
    public List<JourDeGarde> getAllJourDeGardes() {
        log.debug("REST request to get all JourDeGardes");
        return jourDeGardeService.findAll();
    }

    /**
     * {@code GET  /jour-de-gardes/:id} : get the "id" jourDeGarde.
     *
     * @param id the id of the jourDeGarde to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jourDeGarde, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jour-de-gardes/{id}")
    public ResponseEntity<JourDeGarde> getJourDeGarde(@PathVariable Long id) {
        log.debug("REST request to get JourDeGarde : {}", id);
        Optional<JourDeGarde> jourDeGarde = jourDeGardeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jourDeGarde);
    }

    /**
     * {@code DELETE  /jour-de-gardes/:id} : delete the "id" jourDeGarde.
     *
     * @param id the id of the jourDeGarde to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jour-de-gardes/{id}")
    public ResponseEntity<Void> deleteJourDeGarde(@PathVariable Long id) {
        log.debug("REST request to delete JourDeGarde : {}", id);
        jourDeGardeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
