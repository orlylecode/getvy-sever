package com.cogitech.getvy.web.rest;

import com.cogitech.getvy.GetvySeverApp;
import com.cogitech.getvy.domain.Laboratoire;
import com.cogitech.getvy.repository.LaboratoireRepository;
import com.cogitech.getvy.service.LaboratoireService;
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
 * Integration tests for the {@link LaboratoireResource} REST controller.
 */
@SpringBootTest(classes = GetvySeverApp.class)
public class LaboratoireResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final String DEFAULT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_LIEU = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_RUE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_RUE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_TEL = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_HEURE_OUVERTURE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEURE_OUVERTURE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_HEURE_OUVERTURE = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_HEURE_FERMETURE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEURE_FERMETURE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_HEURE_FERMETURE = Instant.ofEpochMilli(-1L);

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    @Autowired
    private LaboratoireRepository laboratoireRepository;

    @Autowired
    private LaboratoireService laboratoireService;

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

    private MockMvc restLaboratoireMockMvc;

    private Laboratoire laboratoire;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LaboratoireResource laboratoireResource = new LaboratoireResource(laboratoireService);
        this.restLaboratoireMockMvc = MockMvcBuilders.standaloneSetup(laboratoireResource)
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
    public static Laboratoire createEntity(EntityManager em) {
        Laboratoire laboratoire = new Laboratoire()
            .nom(DEFAULT_NOM)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .lieu(DEFAULT_LIEU)
            .adresseRue(DEFAULT_ADRESSE_RUE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .numeroTel(DEFAULT_NUMERO_TEL)
            .email(DEFAULT_EMAIL)
            .heureOuverture(DEFAULT_HEURE_OUVERTURE)
            .heureFermeture(DEFAULT_HEURE_FERMETURE)
            .ville(DEFAULT_VILLE)
            .region(DEFAULT_REGION);
        return laboratoire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Laboratoire createUpdatedEntity(EntityManager em) {
        Laboratoire laboratoire = new Laboratoire()
            .nom(UPDATED_NOM)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .lieu(UPDATED_LIEU)
            .adresseRue(UPDATED_ADRESSE_RUE)
            .codePostal(UPDATED_CODE_POSTAL)
            .numeroTel(UPDATED_NUMERO_TEL)
            .email(UPDATED_EMAIL)
            .heureOuverture(UPDATED_HEURE_OUVERTURE)
            .heureFermeture(UPDATED_HEURE_FERMETURE)
            .ville(UPDATED_VILLE)
            .region(UPDATED_REGION);
        return laboratoire;
    }

    @BeforeEach
    public void initTest() {
        laboratoire = createEntity(em);
    }

    @Test
    @Transactional
    public void createLaboratoire() throws Exception {
        int databaseSizeBeforeCreate = laboratoireRepository.findAll().size();

        // Create the Laboratoire
        restLaboratoireMockMvc.perform(post("/api/laboratoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratoire)))
            .andExpect(status().isCreated());

        // Validate the Laboratoire in the database
        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeCreate + 1);
        Laboratoire testLaboratoire = laboratoireList.get(laboratoireList.size() - 1);
        assertThat(testLaboratoire.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testLaboratoire.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testLaboratoire.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testLaboratoire.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testLaboratoire.getAdresseRue()).isEqualTo(DEFAULT_ADRESSE_RUE);
        assertThat(testLaboratoire.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testLaboratoire.getNumeroTel()).isEqualTo(DEFAULT_NUMERO_TEL);
        assertThat(testLaboratoire.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLaboratoire.getHeureOuverture()).isEqualTo(DEFAULT_HEURE_OUVERTURE);
        assertThat(testLaboratoire.getHeureFermeture()).isEqualTo(DEFAULT_HEURE_FERMETURE);
        assertThat(testLaboratoire.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testLaboratoire.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    @Transactional
    public void createLaboratoireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = laboratoireRepository.findAll().size();

        // Create the Laboratoire with an existing ID
        laboratoire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLaboratoireMockMvc.perform(post("/api/laboratoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratoire)))
            .andExpect(status().isBadRequest());

        // Validate the Laboratoire in the database
        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratoireRepository.findAll().size();
        // set the field null
        laboratoire.setNom(null);

        // Create the Laboratoire, which fails.

        restLaboratoireMockMvc.perform(post("/api/laboratoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratoire)))
            .andExpect(status().isBadRequest());

        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLaboratoires() throws Exception {
        // Initialize the database
        laboratoireRepository.saveAndFlush(laboratoire);

        // Get all the laboratoireList
        restLaboratoireMockMvc.perform(get("/api/laboratoires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(laboratoire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU.toString())))
            .andExpect(jsonPath("$.[*].adresseRue").value(hasItem(DEFAULT_ADRESSE_RUE.toString())))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL.toString())))
            .andExpect(jsonPath("$.[*].numeroTel").value(hasItem(DEFAULT_NUMERO_TEL.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].heureOuverture").value(hasItem(DEFAULT_HEURE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].heureFermeture").value(hasItem(DEFAULT_HEURE_FERMETURE.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())));
    }
    
    @Test
    @Transactional
    public void getLaboratoire() throws Exception {
        // Initialize the database
        laboratoireRepository.saveAndFlush(laboratoire);

        // Get the laboratoire
        restLaboratoireMockMvc.perform(get("/api/laboratoires/{id}", laboratoire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(laboratoire.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU.toString()))
            .andExpect(jsonPath("$.adresseRue").value(DEFAULT_ADRESSE_RUE.toString()))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL.toString()))
            .andExpect(jsonPath("$.numeroTel").value(DEFAULT_NUMERO_TEL.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.heureOuverture").value(DEFAULT_HEURE_OUVERTURE.toString()))
            .andExpect(jsonPath("$.heureFermeture").value(DEFAULT_HEURE_FERMETURE.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLaboratoire() throws Exception {
        // Get the laboratoire
        restLaboratoireMockMvc.perform(get("/api/laboratoires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLaboratoire() throws Exception {
        // Initialize the database
        laboratoireService.save(laboratoire);

        int databaseSizeBeforeUpdate = laboratoireRepository.findAll().size();

        // Update the laboratoire
        Laboratoire updatedLaboratoire = laboratoireRepository.findById(laboratoire.getId()).get();
        // Disconnect from session so that the updates on updatedLaboratoire are not directly saved in db
        em.detach(updatedLaboratoire);
        updatedLaboratoire
            .nom(UPDATED_NOM)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .lieu(UPDATED_LIEU)
            .adresseRue(UPDATED_ADRESSE_RUE)
            .codePostal(UPDATED_CODE_POSTAL)
            .numeroTel(UPDATED_NUMERO_TEL)
            .email(UPDATED_EMAIL)
            .heureOuverture(UPDATED_HEURE_OUVERTURE)
            .heureFermeture(UPDATED_HEURE_FERMETURE)
            .ville(UPDATED_VILLE)
            .region(UPDATED_REGION);

        restLaboratoireMockMvc.perform(put("/api/laboratoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLaboratoire)))
            .andExpect(status().isOk());

        // Validate the Laboratoire in the database
        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeUpdate);
        Laboratoire testLaboratoire = laboratoireList.get(laboratoireList.size() - 1);
        assertThat(testLaboratoire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testLaboratoire.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testLaboratoire.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testLaboratoire.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testLaboratoire.getAdresseRue()).isEqualTo(UPDATED_ADRESSE_RUE);
        assertThat(testLaboratoire.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testLaboratoire.getNumeroTel()).isEqualTo(UPDATED_NUMERO_TEL);
        assertThat(testLaboratoire.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLaboratoire.getHeureOuverture()).isEqualTo(UPDATED_HEURE_OUVERTURE);
        assertThat(testLaboratoire.getHeureFermeture()).isEqualTo(UPDATED_HEURE_FERMETURE);
        assertThat(testLaboratoire.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testLaboratoire.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    @Transactional
    public void updateNonExistingLaboratoire() throws Exception {
        int databaseSizeBeforeUpdate = laboratoireRepository.findAll().size();

        // Create the Laboratoire

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLaboratoireMockMvc.perform(put("/api/laboratoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratoire)))
            .andExpect(status().isBadRequest());

        // Validate the Laboratoire in the database
        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLaboratoire() throws Exception {
        // Initialize the database
        laboratoireService.save(laboratoire);

        int databaseSizeBeforeDelete = laboratoireRepository.findAll().size();

        // Delete the laboratoire
        restLaboratoireMockMvc.perform(delete("/api/laboratoires/{id}", laboratoire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Laboratoire.class);
        Laboratoire laboratoire1 = new Laboratoire();
        laboratoire1.setId(1L);
        Laboratoire laboratoire2 = new Laboratoire();
        laboratoire2.setId(laboratoire1.getId());
        assertThat(laboratoire1).isEqualTo(laboratoire2);
        laboratoire2.setId(2L);
        assertThat(laboratoire1).isNotEqualTo(laboratoire2);
        laboratoire1.setId(null);
        assertThat(laboratoire1).isNotEqualTo(laboratoire2);
    }
}
