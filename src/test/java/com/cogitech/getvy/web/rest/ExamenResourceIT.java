package com.cogitech.getvy.web.rest;

import com.cogitech.getvy.GetvySeverApp;
import com.cogitech.getvy.domain.Examen;
import com.cogitech.getvy.repository.ExamenRepository;
import com.cogitech.getvy.service.ExamenService;
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
import java.math.BigDecimal;
import java.util.List;

import static com.cogitech.getvy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExamenResource} REST controller.
 */
@SpringBootTest(classes = GetvySeverApp.class)
public class ExamenResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRIX = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRIX = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_DISPONIBILITE = false;
    private static final Boolean UPDATED_DISPONIBILITE = true;

    @Autowired
    private ExamenRepository examenRepository;

    @Autowired
    private ExamenService examenService;

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

    private MockMvc restExamenMockMvc;

    private Examen examen;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamenResource examenResource = new ExamenResource(examenService);
        this.restExamenMockMvc = MockMvcBuilders.standaloneSetup(examenResource)
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
    public static Examen createEntity(EntityManager em) {
        Examen examen = new Examen()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .prix(DEFAULT_PRIX)
            .disponibilite(DEFAULT_DISPONIBILITE);
        return examen;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examen createUpdatedEntity(EntityManager em) {
        Examen examen = new Examen()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .prix(UPDATED_PRIX)
            .disponibilite(UPDATED_DISPONIBILITE);
        return examen;
    }

    @BeforeEach
    public void initTest() {
        examen = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamen() throws Exception {
        int databaseSizeBeforeCreate = examenRepository.findAll().size();

        // Create the Examen
        restExamenMockMvc.perform(post("/api/examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examen)))
            .andExpect(status().isCreated());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeCreate + 1);
        Examen testExamen = examenList.get(examenList.size() - 1);
        assertThat(testExamen.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testExamen.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testExamen.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testExamen.isDisponibilite()).isEqualTo(DEFAULT_DISPONIBILITE);
    }

    @Test
    @Transactional
    public void createExamenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examenRepository.findAll().size();

        // Create the Examen with an existing ID
        examen.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamenMockMvc.perform(post("/api/examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examen)))
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = examenRepository.findAll().size();
        // set the field null
        examen.setNom(null);

        // Create the Examen, which fails.

        restExamenMockMvc.perform(post("/api/examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examen)))
            .andExpect(status().isBadRequest());

        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        // Get all the examenList
        restExamenMockMvc.perform(get("/api/examen?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examen.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.intValue())))
            .andExpect(jsonPath("$.[*].disponibilite").value(hasItem(DEFAULT_DISPONIBILITE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        // Get the examen
        restExamenMockMvc.perform(get("/api/examen/{id}", examen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examen.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.intValue()))
            .andExpect(jsonPath("$.disponibilite").value(DEFAULT_DISPONIBILITE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExamen() throws Exception {
        // Get the examen
        restExamenMockMvc.perform(get("/api/examen/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamen() throws Exception {
        // Initialize the database
        examenService.save(examen);

        int databaseSizeBeforeUpdate = examenRepository.findAll().size();

        // Update the examen
        Examen updatedExamen = examenRepository.findById(examen.getId()).get();
        // Disconnect from session so that the updates on updatedExamen are not directly saved in db
        em.detach(updatedExamen);
        updatedExamen
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .prix(UPDATED_PRIX)
            .disponibilite(UPDATED_DISPONIBILITE);

        restExamenMockMvc.perform(put("/api/examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExamen)))
            .andExpect(status().isOk());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
        Examen testExamen = examenList.get(examenList.size() - 1);
        assertThat(testExamen.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testExamen.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testExamen.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testExamen.isDisponibilite()).isEqualTo(UPDATED_DISPONIBILITE);
    }

    @Test
    @Transactional
    public void updateNonExistingExamen() throws Exception {
        int databaseSizeBeforeUpdate = examenRepository.findAll().size();

        // Create the Examen

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamenMockMvc.perform(put("/api/examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examen)))
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamen() throws Exception {
        // Initialize the database
        examenService.save(examen);

        int databaseSizeBeforeDelete = examenRepository.findAll().size();

        // Delete the examen
        restExamenMockMvc.perform(delete("/api/examen/{id}", examen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Examen.class);
        Examen examen1 = new Examen();
        examen1.setId(1L);
        Examen examen2 = new Examen();
        examen2.setId(examen1.getId());
        assertThat(examen1).isEqualTo(examen2);
        examen2.setId(2L);
        assertThat(examen1).isNotEqualTo(examen2);
        examen1.setId(null);
        assertThat(examen1).isNotEqualTo(examen2);
    }
}
