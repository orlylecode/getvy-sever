package com.cogitech.getvy.web.rest;

import com.cogitech.getvy.GetvySeverApp;
import com.cogitech.getvy.domain.JourDeGarde;
import com.cogitech.getvy.repository.JourDeGardeRepository;
import com.cogitech.getvy.service.JourDeGardeService;
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
 * Integration tests for the {@link JourDeGardeResource} REST controller.
 */
@SpringBootTest(classes = GetvySeverApp.class)
public class JourDeGardeResourceIT {

    private static final Instant DEFAULT_JOUR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JOUR = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_JOUR = Instant.ofEpochMilli(-1L);

    @Autowired
    private JourDeGardeRepository jourDeGardeRepository;

    @Autowired
    private JourDeGardeService jourDeGardeService;

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

    private MockMvc restJourDeGardeMockMvc;

    private JourDeGarde jourDeGarde;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JourDeGardeResource jourDeGardeResource = new JourDeGardeResource(jourDeGardeService);
        this.restJourDeGardeMockMvc = MockMvcBuilders.standaloneSetup(jourDeGardeResource)
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
    public static JourDeGarde createEntity(EntityManager em) {
        JourDeGarde jourDeGarde = new JourDeGarde()
            .jour(DEFAULT_JOUR);
        return jourDeGarde;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourDeGarde createUpdatedEntity(EntityManager em) {
        JourDeGarde jourDeGarde = new JourDeGarde()
            .jour(UPDATED_JOUR);
        return jourDeGarde;
    }

    @BeforeEach
    public void initTest() {
        jourDeGarde = createEntity(em);
    }

    @Test
    @Transactional
    public void createJourDeGarde() throws Exception {
        int databaseSizeBeforeCreate = jourDeGardeRepository.findAll().size();

        // Create the JourDeGarde
        restJourDeGardeMockMvc.perform(post("/api/jour-de-gardes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jourDeGarde)))
            .andExpect(status().isCreated());

        // Validate the JourDeGarde in the database
        List<JourDeGarde> jourDeGardeList = jourDeGardeRepository.findAll();
        assertThat(jourDeGardeList).hasSize(databaseSizeBeforeCreate + 1);
        JourDeGarde testJourDeGarde = jourDeGardeList.get(jourDeGardeList.size() - 1);
        assertThat(testJourDeGarde.getJour()).isEqualTo(DEFAULT_JOUR);
    }

    @Test
    @Transactional
    public void createJourDeGardeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jourDeGardeRepository.findAll().size();

        // Create the JourDeGarde with an existing ID
        jourDeGarde.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourDeGardeMockMvc.perform(post("/api/jour-de-gardes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jourDeGarde)))
            .andExpect(status().isBadRequest());

        // Validate the JourDeGarde in the database
        List<JourDeGarde> jourDeGardeList = jourDeGardeRepository.findAll();
        assertThat(jourDeGardeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkJourIsRequired() throws Exception {
        int databaseSizeBeforeTest = jourDeGardeRepository.findAll().size();
        // set the field null
        jourDeGarde.setJour(null);

        // Create the JourDeGarde, which fails.

        restJourDeGardeMockMvc.perform(post("/api/jour-de-gardes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jourDeGarde)))
            .andExpect(status().isBadRequest());

        List<JourDeGarde> jourDeGardeList = jourDeGardeRepository.findAll();
        assertThat(jourDeGardeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJourDeGardes() throws Exception {
        // Initialize the database
        jourDeGardeRepository.saveAndFlush(jourDeGarde);

        // Get all the jourDeGardeList
        restJourDeGardeMockMvc.perform(get("/api/jour-de-gardes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jourDeGarde.getId().intValue())))
            .andExpect(jsonPath("$.[*].jour").value(hasItem(DEFAULT_JOUR.toString())));
    }
    
    @Test
    @Transactional
    public void getJourDeGarde() throws Exception {
        // Initialize the database
        jourDeGardeRepository.saveAndFlush(jourDeGarde);

        // Get the jourDeGarde
        restJourDeGardeMockMvc.perform(get("/api/jour-de-gardes/{id}", jourDeGarde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jourDeGarde.getId().intValue()))
            .andExpect(jsonPath("$.jour").value(DEFAULT_JOUR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJourDeGarde() throws Exception {
        // Get the jourDeGarde
        restJourDeGardeMockMvc.perform(get("/api/jour-de-gardes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJourDeGarde() throws Exception {
        // Initialize the database
        jourDeGardeService.save(jourDeGarde);

        int databaseSizeBeforeUpdate = jourDeGardeRepository.findAll().size();

        // Update the jourDeGarde
        JourDeGarde updatedJourDeGarde = jourDeGardeRepository.findById(jourDeGarde.getId()).get();
        // Disconnect from session so that the updates on updatedJourDeGarde are not directly saved in db
        em.detach(updatedJourDeGarde);
        updatedJourDeGarde
            .jour(UPDATED_JOUR);

        restJourDeGardeMockMvc.perform(put("/api/jour-de-gardes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJourDeGarde)))
            .andExpect(status().isOk());

        // Validate the JourDeGarde in the database
        List<JourDeGarde> jourDeGardeList = jourDeGardeRepository.findAll();
        assertThat(jourDeGardeList).hasSize(databaseSizeBeforeUpdate);
        JourDeGarde testJourDeGarde = jourDeGardeList.get(jourDeGardeList.size() - 1);
        assertThat(testJourDeGarde.getJour()).isEqualTo(UPDATED_JOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingJourDeGarde() throws Exception {
        int databaseSizeBeforeUpdate = jourDeGardeRepository.findAll().size();

        // Create the JourDeGarde

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourDeGardeMockMvc.perform(put("/api/jour-de-gardes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jourDeGarde)))
            .andExpect(status().isBadRequest());

        // Validate the JourDeGarde in the database
        List<JourDeGarde> jourDeGardeList = jourDeGardeRepository.findAll();
        assertThat(jourDeGardeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJourDeGarde() throws Exception {
        // Initialize the database
        jourDeGardeService.save(jourDeGarde);

        int databaseSizeBeforeDelete = jourDeGardeRepository.findAll().size();

        // Delete the jourDeGarde
        restJourDeGardeMockMvc.perform(delete("/api/jour-de-gardes/{id}", jourDeGarde.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JourDeGarde> jourDeGardeList = jourDeGardeRepository.findAll();
        assertThat(jourDeGardeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourDeGarde.class);
        JourDeGarde jourDeGarde1 = new JourDeGarde();
        jourDeGarde1.setId(1L);
        JourDeGarde jourDeGarde2 = new JourDeGarde();
        jourDeGarde2.setId(jourDeGarde1.getId());
        assertThat(jourDeGarde1).isEqualTo(jourDeGarde2);
        jourDeGarde2.setId(2L);
        assertThat(jourDeGarde1).isNotEqualTo(jourDeGarde2);
        jourDeGarde1.setId(null);
        assertThat(jourDeGarde1).isNotEqualTo(jourDeGarde2);
    }
}
