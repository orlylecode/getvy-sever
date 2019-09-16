package com.cogitech.getvy.web.rest;

import com.cogitech.getvy.GetvySeverApp;
import com.cogitech.getvy.domain.Pharmacie;
import com.cogitech.getvy.repository.PharmacieRepository;
import com.cogitech.getvy.service.PharmacieService;
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
 * Integration tests for the {@link PharmacieResource} REST controller.
 */
@SpringBootTest(classes = GetvySeverApp.class)
public class PharmacieResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

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

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final Instant DEFAULT_HEURE_OUVERTURE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEURE_OUVERTURE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_HEURE_OUVERTURE = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_HEURE_FERMETURE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEURE_FERMETURE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_HEURE_FERMETURE = Instant.ofEpochMilli(-1L);

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    @Autowired
    private PharmacieRepository pharmacieRepository;

    @Autowired
    private PharmacieService pharmacieService;

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

    private MockMvc restPharmacieMockMvc;

    private Pharmacie pharmacie;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PharmacieResource pharmacieResource = new PharmacieResource(pharmacieService);
        this.restPharmacieMockMvc = MockMvcBuilders.standaloneSetup(pharmacieResource)
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
    public static Pharmacie createEntity(EntityManager em) {
        Pharmacie pharmacie = new Pharmacie()
            .nom(DEFAULT_NOM)
            .lieu(DEFAULT_LIEU)
            .adresseRue(DEFAULT_ADRESSE_RUE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .numeroTel(DEFAULT_NUMERO_TEL)
            .email(DEFAULT_EMAIL)
            .ville(DEFAULT_VILLE)
            .region(DEFAULT_REGION)
            .heureOuverture(DEFAULT_HEURE_OUVERTURE)
            .heureFermeture(DEFAULT_HEURE_FERMETURE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return pharmacie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pharmacie createUpdatedEntity(EntityManager em) {
        Pharmacie pharmacie = new Pharmacie()
            .nom(UPDATED_NOM)
            .lieu(UPDATED_LIEU)
            .adresseRue(UPDATED_ADRESSE_RUE)
            .codePostal(UPDATED_CODE_POSTAL)
            .numeroTel(UPDATED_NUMERO_TEL)
            .email(UPDATED_EMAIL)
            .ville(UPDATED_VILLE)
            .region(UPDATED_REGION)
            .heureOuverture(UPDATED_HEURE_OUVERTURE)
            .heureFermeture(UPDATED_HEURE_FERMETURE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        return pharmacie;
    }

    @BeforeEach
    public void initTest() {
        pharmacie = createEntity(em);
    }

    @Test
    @Transactional
    public void createPharmacie() throws Exception {
        int databaseSizeBeforeCreate = pharmacieRepository.findAll().size();

        // Create the Pharmacie
        restPharmacieMockMvc.perform(post("/api/pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacie)))
            .andExpect(status().isCreated());

        // Validate the Pharmacie in the database
        List<Pharmacie> pharmacieList = pharmacieRepository.findAll();
        assertThat(pharmacieList).hasSize(databaseSizeBeforeCreate + 1);
        Pharmacie testPharmacie = pharmacieList.get(pharmacieList.size() - 1);
        assertThat(testPharmacie.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPharmacie.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testPharmacie.getAdresseRue()).isEqualTo(DEFAULT_ADRESSE_RUE);
        assertThat(testPharmacie.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testPharmacie.getNumeroTel()).isEqualTo(DEFAULT_NUMERO_TEL);
        assertThat(testPharmacie.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPharmacie.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testPharmacie.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testPharmacie.getHeureOuverture()).isEqualTo(DEFAULT_HEURE_OUVERTURE);
        assertThat(testPharmacie.getHeureFermeture()).isEqualTo(DEFAULT_HEURE_FERMETURE);
        assertThat(testPharmacie.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testPharmacie.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createPharmacieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pharmacieRepository.findAll().size();

        // Create the Pharmacie with an existing ID
        pharmacie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPharmacieMockMvc.perform(post("/api/pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacie)))
            .andExpect(status().isBadRequest());

        // Validate the Pharmacie in the database
        List<Pharmacie> pharmacieList = pharmacieRepository.findAll();
        assertThat(pharmacieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = pharmacieRepository.findAll().size();
        // set the field null
        pharmacie.setNom(null);

        // Create the Pharmacie, which fails.

        restPharmacieMockMvc.perform(post("/api/pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacie)))
            .andExpect(status().isBadRequest());

        List<Pharmacie> pharmacieList = pharmacieRepository.findAll();
        assertThat(pharmacieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPharmacies() throws Exception {
        // Initialize the database
        pharmacieRepository.saveAndFlush(pharmacie);

        // Get all the pharmacieList
        restPharmacieMockMvc.perform(get("/api/pharmacies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pharmacie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU.toString())))
            .andExpect(jsonPath("$.[*].adresseRue").value(hasItem(DEFAULT_ADRESSE_RUE.toString())))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL.toString())))
            .andExpect(jsonPath("$.[*].numeroTel").value(hasItem(DEFAULT_NUMERO_TEL.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].heureOuverture").value(hasItem(DEFAULT_HEURE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].heureFermeture").value(hasItem(DEFAULT_HEURE_FERMETURE.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPharmacie() throws Exception {
        // Initialize the database
        pharmacieRepository.saveAndFlush(pharmacie);

        // Get the pharmacie
        restPharmacieMockMvc.perform(get("/api/pharmacies/{id}", pharmacie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pharmacie.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU.toString()))
            .andExpect(jsonPath("$.adresseRue").value(DEFAULT_ADRESSE_RUE.toString()))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL.toString()))
            .andExpect(jsonPath("$.numeroTel").value(DEFAULT_NUMERO_TEL.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.heureOuverture").value(DEFAULT_HEURE_OUVERTURE.toString()))
            .andExpect(jsonPath("$.heureFermeture").value(DEFAULT_HEURE_FERMETURE.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPharmacie() throws Exception {
        // Get the pharmacie
        restPharmacieMockMvc.perform(get("/api/pharmacies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePharmacie() throws Exception {
        // Initialize the database
        pharmacieService.save(pharmacie);

        int databaseSizeBeforeUpdate = pharmacieRepository.findAll().size();

        // Update the pharmacie
        Pharmacie updatedPharmacie = pharmacieRepository.findById(pharmacie.getId()).get();
        // Disconnect from session so that the updates on updatedPharmacie are not directly saved in db
        em.detach(updatedPharmacie);
        updatedPharmacie
            .nom(UPDATED_NOM)
            .lieu(UPDATED_LIEU)
            .adresseRue(UPDATED_ADRESSE_RUE)
            .codePostal(UPDATED_CODE_POSTAL)
            .numeroTel(UPDATED_NUMERO_TEL)
            .email(UPDATED_EMAIL)
            .ville(UPDATED_VILLE)
            .region(UPDATED_REGION)
            .heureOuverture(UPDATED_HEURE_OUVERTURE)
            .heureFermeture(UPDATED_HEURE_FERMETURE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restPharmacieMockMvc.perform(put("/api/pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPharmacie)))
            .andExpect(status().isOk());

        // Validate the Pharmacie in the database
        List<Pharmacie> pharmacieList = pharmacieRepository.findAll();
        assertThat(pharmacieList).hasSize(databaseSizeBeforeUpdate);
        Pharmacie testPharmacie = pharmacieList.get(pharmacieList.size() - 1);
        assertThat(testPharmacie.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPharmacie.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testPharmacie.getAdresseRue()).isEqualTo(UPDATED_ADRESSE_RUE);
        assertThat(testPharmacie.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testPharmacie.getNumeroTel()).isEqualTo(UPDATED_NUMERO_TEL);
        assertThat(testPharmacie.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPharmacie.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testPharmacie.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testPharmacie.getHeureOuverture()).isEqualTo(UPDATED_HEURE_OUVERTURE);
        assertThat(testPharmacie.getHeureFermeture()).isEqualTo(UPDATED_HEURE_FERMETURE);
        assertThat(testPharmacie.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testPharmacie.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingPharmacie() throws Exception {
        int databaseSizeBeforeUpdate = pharmacieRepository.findAll().size();

        // Create the Pharmacie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPharmacieMockMvc.perform(put("/api/pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacie)))
            .andExpect(status().isBadRequest());

        // Validate the Pharmacie in the database
        List<Pharmacie> pharmacieList = pharmacieRepository.findAll();
        assertThat(pharmacieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePharmacie() throws Exception {
        // Initialize the database
        pharmacieService.save(pharmacie);

        int databaseSizeBeforeDelete = pharmacieRepository.findAll().size();

        // Delete the pharmacie
        restPharmacieMockMvc.perform(delete("/api/pharmacies/{id}", pharmacie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pharmacie> pharmacieList = pharmacieRepository.findAll();
        assertThat(pharmacieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pharmacie.class);
        Pharmacie pharmacie1 = new Pharmacie();
        pharmacie1.setId(1L);
        Pharmacie pharmacie2 = new Pharmacie();
        pharmacie2.setId(pharmacie1.getId());
        assertThat(pharmacie1).isEqualTo(pharmacie2);
        pharmacie2.setId(2L);
        assertThat(pharmacie1).isNotEqualTo(pharmacie2);
        pharmacie1.setId(null);
        assertThat(pharmacie1).isNotEqualTo(pharmacie2);
    }
}
