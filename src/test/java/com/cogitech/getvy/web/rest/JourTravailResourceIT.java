package com.cogitech.getvy.web.rest;

import com.cogitech.getvy.GetvySeverApp;
import com.cogitech.getvy.domain.JourTravail;
import com.cogitech.getvy.repository.JourTravailRepository;
import com.cogitech.getvy.service.JourTravailService;
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
 * Integration tests for the {@link JourTravailResource} REST controller.
 */
@SpringBootTest(classes = GetvySeverApp.class)
public class JourTravailResourceIT {

    private static final Instant DEFAULT_JOUR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JOUR = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_JOUR = Instant.ofEpochMilli(-1L);

    @Autowired
    private JourTravailRepository jourTravailRepository;

    @Autowired
    private JourTravailService jourTravailService;

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

    private MockMvc restJourTravailMockMvc;

    private JourTravail jourTravail;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JourTravailResource jourTravailResource = new JourTravailResource(jourTravailService);
        this.restJourTravailMockMvc = MockMvcBuilders.standaloneSetup(jourTravailResource)
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
    public static JourTravail createEntity(EntityManager em) {
        JourTravail jourTravail = new JourTravail()
            .jour(DEFAULT_JOUR);
        return jourTravail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourTravail createUpdatedEntity(EntityManager em) {
        JourTravail jourTravail = new JourTravail()
            .jour(UPDATED_JOUR);
        return jourTravail;
    }

    @BeforeEach
    public void initTest() {
        jourTravail = createEntity(em);
    }

    @Test
    @Transactional
    public void createJourTravail() throws Exception {
        int databaseSizeBeforeCreate = jourTravailRepository.findAll().size();

        // Create the JourTravail
        restJourTravailMockMvc.perform(post("/api/jour-travails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jourTravail)))
            .andExpect(status().isCreated());

        // Validate the JourTravail in the database
        List<JourTravail> jourTravailList = jourTravailRepository.findAll();
        assertThat(jourTravailList).hasSize(databaseSizeBeforeCreate + 1);
        JourTravail testJourTravail = jourTravailList.get(jourTravailList.size() - 1);
        assertThat(testJourTravail.getJour()).isEqualTo(DEFAULT_JOUR);
    }

    @Test
    @Transactional
    public void createJourTravailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jourTravailRepository.findAll().size();

        // Create the JourTravail with an existing ID
        jourTravail.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourTravailMockMvc.perform(post("/api/jour-travails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jourTravail)))
            .andExpect(status().isBadRequest());

        // Validate the JourTravail in the database
        List<JourTravail> jourTravailList = jourTravailRepository.findAll();
        assertThat(jourTravailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkJourIsRequired() throws Exception {
        int databaseSizeBeforeTest = jourTravailRepository.findAll().size();
        // set the field null
        jourTravail.setJour(null);

        // Create the JourTravail, which fails.

        restJourTravailMockMvc.perform(post("/api/jour-travails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jourTravail)))
            .andExpect(status().isBadRequest());

        List<JourTravail> jourTravailList = jourTravailRepository.findAll();
        assertThat(jourTravailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJourTravails() throws Exception {
        // Initialize the database
        jourTravailRepository.saveAndFlush(jourTravail);

        // Get all the jourTravailList
        restJourTravailMockMvc.perform(get("/api/jour-travails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jourTravail.getId().intValue())))
            .andExpect(jsonPath("$.[*].jour").value(hasItem(DEFAULT_JOUR.toString())));
    }
    
    @Test
    @Transactional
    public void getJourTravail() throws Exception {
        // Initialize the database
        jourTravailRepository.saveAndFlush(jourTravail);

        // Get the jourTravail
        restJourTravailMockMvc.perform(get("/api/jour-travails/{id}", jourTravail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jourTravail.getId().intValue()))
            .andExpect(jsonPath("$.jour").value(DEFAULT_JOUR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJourTravail() throws Exception {
        // Get the jourTravail
        restJourTravailMockMvc.perform(get("/api/jour-travails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJourTravail() throws Exception {
        // Initialize the database
        jourTravailService.save(jourTravail);

        int databaseSizeBeforeUpdate = jourTravailRepository.findAll().size();

        // Update the jourTravail
        JourTravail updatedJourTravail = jourTravailRepository.findById(jourTravail.getId()).get();
        // Disconnect from session so that the updates on updatedJourTravail are not directly saved in db
        em.detach(updatedJourTravail);
        updatedJourTravail
            .jour(UPDATED_JOUR);

        restJourTravailMockMvc.perform(put("/api/jour-travails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJourTravail)))
            .andExpect(status().isOk());

        // Validate the JourTravail in the database
        List<JourTravail> jourTravailList = jourTravailRepository.findAll();
        assertThat(jourTravailList).hasSize(databaseSizeBeforeUpdate);
        JourTravail testJourTravail = jourTravailList.get(jourTravailList.size() - 1);
        assertThat(testJourTravail.getJour()).isEqualTo(UPDATED_JOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingJourTravail() throws Exception {
        int databaseSizeBeforeUpdate = jourTravailRepository.findAll().size();

        // Create the JourTravail

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourTravailMockMvc.perform(put("/api/jour-travails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jourTravail)))
            .andExpect(status().isBadRequest());

        // Validate the JourTravail in the database
        List<JourTravail> jourTravailList = jourTravailRepository.findAll();
        assertThat(jourTravailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJourTravail() throws Exception {
        // Initialize the database
        jourTravailService.save(jourTravail);

        int databaseSizeBeforeDelete = jourTravailRepository.findAll().size();

        // Delete the jourTravail
        restJourTravailMockMvc.perform(delete("/api/jour-travails/{id}", jourTravail.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JourTravail> jourTravailList = jourTravailRepository.findAll();
        assertThat(jourTravailList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourTravail.class);
        JourTravail jourTravail1 = new JourTravail();
        jourTravail1.setId(1L);
        JourTravail jourTravail2 = new JourTravail();
        jourTravail2.setId(jourTravail1.getId());
        assertThat(jourTravail1).isEqualTo(jourTravail2);
        jourTravail2.setId(2L);
        assertThat(jourTravail1).isNotEqualTo(jourTravail2);
        jourTravail1.setId(null);
        assertThat(jourTravail1).isNotEqualTo(jourTravail2);
    }
}
