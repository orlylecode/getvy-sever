package com.cogitech.getvy.web.rest;

import com.cogitech.getvy.GetvySeverApp;
import com.cogitech.getvy.domain.Cycle;
import com.cogitech.getvy.repository.CycleRepository;
import com.cogitech.getvy.service.CycleService;
import com.cogitech.getvy.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.cogitech.getvy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CycleResource} REST controller.
 */
@SpringBootTest(classes = GetvySeverApp.class)
public class CycleResourceIT {

    private static final Instant DEFAULT_DATE_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_DATE_DEBUT = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_DATE_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_DATE_FIN = Instant.ofEpochMilli(-1L);

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private CycleService cycleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCycleMockMvc;

    private Cycle cycle;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CycleResource cycleResource = new CycleResource(cycleService);
        this.restCycleMockMvc = MockMvcBuilders.standaloneSetup(cycleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cycle createEntity(EntityManager em) {
        Cycle cycle = new Cycle()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .libelle(DEFAULT_LIBELLE);
        return cycle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cycle createUpdatedEntity(EntityManager em) {
        Cycle cycle = new Cycle()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .libelle(UPDATED_LIBELLE);
        return cycle;
    }

    @BeforeEach
    public void initTest() {
        cycle = createEntity(em);
    }

    @Test
    @Transactional
    public void createCycle() throws Exception {
        int databaseSizeBeforeCreate = cycleRepository.findAll().size();

        // Create the Cycle
        restCycleMockMvc.perform(post("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cycle)))
            .andExpect(status().isCreated());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeCreate + 1);
        Cycle testCycle = cycleList.get(cycleList.size() - 1);
        assertThat(testCycle.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testCycle.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testCycle.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createCycleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cycleRepository.findAll().size();

        // Create the Cycle with an existing ID
        cycle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCycleMockMvc.perform(post("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cycle)))
            .andExpect(status().isBadRequest());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = cycleRepository.findAll().size();
        // set the field null
        cycle.setDateDebut(null);

        // Create the Cycle, which fails.

        restCycleMockMvc.perform(post("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cycle)))
            .andExpect(status().isBadRequest());

        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = cycleRepository.findAll().size();
        // set the field null
        cycle.setDateFin(null);

        // Create the Cycle, which fails.

        restCycleMockMvc.perform(post("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cycle)))
            .andExpect(status().isBadRequest());

        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCycles() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get all the cycleList
        restCycleMockMvc.perform(get("/api/cycles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cycle.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getCycle() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get the cycle
        restCycleMockMvc.perform(get("/api/cycles/{id}", cycle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cycle.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCycle() throws Exception {
        // Get the cycle
        restCycleMockMvc.perform(get("/api/cycles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCycle() throws Exception {
        // Initialize the database
        cycleService.save(cycle);

        int databaseSizeBeforeUpdate = cycleRepository.findAll().size();

        // Update the cycle
        Cycle updatedCycle = cycleRepository.findById(cycle.getId()).get();
        // Disconnect from session so that the updates on updatedCycle are not directly saved in db
        em.detach(updatedCycle);
        updatedCycle
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .libelle(UPDATED_LIBELLE);

        restCycleMockMvc.perform(put("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCycle)))
            .andExpect(status().isOk());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeUpdate);
        Cycle testCycle = cycleList.get(cycleList.size() - 1);
        assertThat(testCycle.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testCycle.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testCycle.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCycle() throws Exception {
        int databaseSizeBeforeUpdate = cycleRepository.findAll().size();

        // Create the Cycle

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCycleMockMvc.perform(put("/api/cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cycle)))
            .andExpect(status().isBadRequest());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCycle() throws Exception {
        // Initialize the database
        cycleService.save(cycle);

        int databaseSizeBeforeDelete = cycleRepository.findAll().size();

        // Delete the cycle
        restCycleMockMvc.perform(delete("/api/cycles/{id}", cycle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cycle.class);
        Cycle cycle1 = new Cycle();
        cycle1.setId(1L);
        Cycle cycle2 = new Cycle();
        cycle2.setId(cycle1.getId());
        assertThat(cycle1).isEqualTo(cycle2);
        cycle2.setId(2L);
        assertThat(cycle1).isNotEqualTo(cycle2);
        cycle1.setId(null);
        assertThat(cycle1).isNotEqualTo(cycle2);
    }
}
