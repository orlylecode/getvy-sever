package com.cogitech.getvy.web.rest;

import com.cogitech.getvy.domain.JourTravail;
import com.cogitech.getvy.service.JourTravailService;
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
 * REST controller for managing {@link com.cogitech.getvy.domain.JourTravail}.
 */
@RestController
@RequestMapping("/api")
public class JourTravailResource {

    private final Logger log = LoggerFactory.getLogger(JourTravailResource.class);

    private static final String ENTITY_NAME = "jourTravail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JourTravailService jourTravailService;

    public JourTravailResource(JourTravailService jourTravailService) {
        this.jourTravailService = jourTravailService;
    }

    /**
     * {@code POST  /jour-travails} : Create a new jourTravail.
     *
     * @param jourTravail the jourTravail to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jourTravail, or with status {@code 400 (Bad Request)} if the jourTravail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jour-travails")
    public ResponseEntity<JourTravail> createJourTravail(@Valid @RequestBody JourTravail jourTravail) throws URISyntaxException {
        log.debug("REST request to save JourTravail : {}", jourTravail);
        if (jourTravail.getId() != null) {
            throw new BadRequestAlertException("A new jourTravail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JourTravail result = jourTravailService.save(jourTravail);
        return ResponseEntity.created(new URI("/api/jour-travails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jour-travails} : Updates an existing jourTravail.
     *
     * @param jourTravail the jourTravail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jourTravail,
     * or with status {@code 400 (Bad Request)} if the jourTravail is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jourTravail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jour-travails")
    public ResponseEntity<JourTravail> updateJourTravail(@Valid @RequestBody JourTravail jourTravail) throws URISyntaxException {
        log.debug("REST request to update JourTravail : {}", jourTravail);
        if (jourTravail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JourTravail result = jourTravailService.save(jourTravail);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jourTravail.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jour-travails} : get all the jourTravails.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jourTravails in body.
     */
    @GetMapping("/jour-travails")
    public List<JourTravail> getAllJourTravails() {
        log.debug("REST request to get all JourTravails");
        return jourTravailService.findAll();
    }

    /**
     * {@code GET  /jour-travails/:id} : get the "id" jourTravail.
     *
     * @param id the id of the jourTravail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jourTravail, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jour-travails/{id}")
    public ResponseEntity<JourTravail> getJourTravail(@PathVariable Long id) {
        log.debug("REST request to get JourTravail : {}", id);
        Optional<JourTravail> jourTravail = jourTravailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jourTravail);
    }

    /**
     * {@code DELETE  /jour-travails/:id} : delete the "id" jourTravail.
     *
     * @param id the id of the jourTravail to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jour-travails/{id}")
    public ResponseEntity<Void> deleteJourTravail(@PathVariable Long id) {
        log.debug("REST request to delete JourTravail : {}", id);
        jourTravailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
