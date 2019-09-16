package com.cogitech.getvy.web.rest;

import com.cogitech.getvy.domain.Pharmacie;
import com.cogitech.getvy.service.PharmacieService;
import com.cogitech.getvy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cogitech.getvy.domain.Pharmacie}.
 */
@RestController
@RequestMapping("/api")
public class PharmacieResource {

    private final Logger log = LoggerFactory.getLogger(PharmacieResource.class);

    private static final String ENTITY_NAME = "pharmacie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PharmacieService pharmacieService;

    public PharmacieResource(PharmacieService pharmacieService) {
        this.pharmacieService = pharmacieService;
    }

    /**
     * {@code POST  /pharmacies} : Create a new pharmacie.
     *
     * @param pharmacie the pharmacie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pharmacie, or with status {@code 400 (Bad Request)} if the pharmacie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pharmacies")
    public ResponseEntity<Pharmacie> createPharmacie(@Valid @RequestBody Pharmacie pharmacie) throws URISyntaxException {
        log.debug("REST request to save Pharmacie : {}", pharmacie);
        if (pharmacie.getId() != null) {
            throw new BadRequestAlertException("A new pharmacie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pharmacie result = pharmacieService.save(pharmacie);
        return ResponseEntity.created(new URI("/api/pharmacies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pharmacies} : Updates an existing pharmacie.
     *
     * @param pharmacie the pharmacie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pharmacie,
     * or with status {@code 400 (Bad Request)} if the pharmacie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pharmacie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pharmacies")
    public ResponseEntity<Pharmacie> updatePharmacie(@Valid @RequestBody Pharmacie pharmacie) throws URISyntaxException {
        log.debug("REST request to update Pharmacie : {}", pharmacie);
        if (pharmacie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pharmacie result = pharmacieService.save(pharmacie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pharmacie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pharmacies} : get all the pharmacies.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pharmacies in body.
     */
    @GetMapping("/pharmacies")
    public ResponseEntity<List<Pharmacie>> getAllPharmacies(Pageable pageable) {
        log.debug("REST request to get a page of Pharmacies");
        Page<Pharmacie> page = pharmacieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pharmacies/:id} : get the "id" pharmacie.
     *
     * @param id the id of the pharmacie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pharmacie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pharmacies/{id}")
    public ResponseEntity<Pharmacie> getPharmacie(@PathVariable Long id) {
        log.debug("REST request to get Pharmacie : {}", id);
        Optional<Pharmacie> pharmacie = pharmacieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pharmacie);
    }

    /**
     * {@code DELETE  /pharmacies/:id} : delete the "id" pharmacie.
     *
     * @param id the id of the pharmacie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pharmacies/{id}")
    public ResponseEntity<Void> deletePharmacie(@PathVariable Long id) {
        log.debug("REST request to delete Pharmacie : {}", id);
        pharmacieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
