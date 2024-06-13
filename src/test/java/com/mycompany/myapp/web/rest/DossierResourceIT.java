package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.DossierAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Dossier;
import com.mycompany.myapp.domain.enumeration.NIVEAUCOMP;
import com.mycompany.myapp.domain.enumeration.NiveauEtude;
import com.mycompany.myapp.domain.enumeration.NomDepartement;
import com.mycompany.myapp.domain.enumeration.NomDepartement;
import com.mycompany.myapp.domain.enumeration.NomFiliere;
import com.mycompany.myapp.domain.enumeration.NomRegion;
import com.mycompany.myapp.domain.enumeration.NomRegion;
import com.mycompany.myapp.domain.enumeration.Sexe;
import com.mycompany.myapp.domain.enumeration.TypePiece;
import com.mycompany.myapp.repository.DossierRepository;
import com.mycompany.myapp.service.DossierService;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DossierResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DossierResourceIT {

    private static final String DEFAULT_NUM_DOSSIER = "AAAAAAAAAA";
    private static final String UPDATED_NUM_DOSSIER = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_UTILISATEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_UTILISATEUR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISS = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_NAISS = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISS = "BBBBBBBBBB";

    private static final NomRegion DEFAULT_REGION_NAISS = NomRegion.DAKAR;
    private static final NomRegion UPDATED_REGION_NAISS = NomRegion.DIOURBEL;

    private static final NomDepartement DEFAULT_DEPARTEMENT_NAISS = NomDepartement.DAKAR;
    private static final NomDepartement UPDATED_DEPARTEMENT_NAISS = NomDepartement.GUEDIAWAYE;

    private static final TypePiece DEFAULT_TYPE_PIECE = TypePiece.CNI;
    private static final TypePiece UPDATED_TYPE_PIECE = TypePiece.PASSEPORT;

    private static final Long DEFAULT_NUMERO_PIECE = 1L;
    private static final Long UPDATED_NUMERO_PIECE = 2L;

    private static final Sexe DEFAULT_SEXE = Sexe.HOMME;
    private static final Sexe UPDATED_SEXE = Sexe.FEMME;

    private static final NomRegion DEFAULT_REGION_RESIDENCE = NomRegion.DAKAR;
    private static final NomRegion UPDATED_REGION_RESIDENCE = NomRegion.DIOURBEL;

    private static final NomDepartement DEFAULT_DEP_RESIDENCE = NomDepartement.DAKAR;
    private static final NomDepartement UPDATED_DEP_RESIDENCE = NomDepartement.GUEDIAWAYE;

    private static final String DEFAULT_ADRESSE_RESIDENCE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_RESIDENCE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final NiveauEtude DEFAULT_NIVEAU_FORMATION = NiveauEtude.CINQUIEME;
    private static final NiveauEtude UPDATED_NIVEAU_FORMATION = NiveauEtude.QUATRIEME;

    private static final NomFiliere DEFAULT_SPECIALITE = NomFiliere.AGRI_ELEVAGE;
    private static final NomFiliere UPDATED_SPECIALITE = NomFiliere.AGRICULTURE;

    private static final String DEFAULT_INTITULE_DIPLOME = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE_DIPLOME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DIPLOME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DIPLOME = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DIPLOME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DIPLOME_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_ANNEE_OBTENTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANNEE_OBTENTION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_OBTENTION = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_OBTENTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CV = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CV = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CV_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CV_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_LETTRE_MOTIVATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LETTRE_MOTIVATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LETTRE_MOTIVATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LETTRE_MOTIVATION_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_AUTRE_SPECIALITE = "AAAAAAAAAA";
    private static final String UPDATED_AUTRE_SPECIALITE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COMPETENCE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COMPETENCE = "BBBBBBBBBB";

    private static final NIVEAUCOMP DEFAULT_NIVEAU_COMPETENCE = NIVEAUCOMP.DEBUTANT;
    private static final NIVEAUCOMP UPDATED_NIVEAU_COMPETENCE = NIVEAUCOMP.INTERMEDIAIRE;

    private static final String DEFAULT_INTITULE_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE_EXPERIENCE = "BBBBBBBBBB";

    private static final String DEFAULT_POSTE_OCCUPE = "AAAAAAAAAA";
    private static final String UPDATED_POSTE_OCCUPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOM_ENTREPRISE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ENTREPRISE = "BBBBBBBBBB";

    private static final String DEFAULT_MISSION = "AAAAAAAAAA";
    private static final String UPDATED_MISSION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dossiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DossierRepository dossierRepository;

    @Mock
    private DossierRepository dossierRepositoryMock;

    @Mock
    private DossierService dossierServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDossierMockMvc;

    private Dossier dossier;

    private Dossier insertedDossier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dossier createEntity(EntityManager em) {
        Dossier dossier = new Dossier()
            .numDossier(DEFAULT_NUM_DOSSIER)
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .nomUtilisateur(DEFAULT_NOM_UTILISATEUR)
            .dateNaiss(DEFAULT_DATE_NAISS)
            .lieuNaiss(DEFAULT_LIEU_NAISS)
            .regionNaiss(DEFAULT_REGION_NAISS)
            .departementNaiss(DEFAULT_DEPARTEMENT_NAISS)
            .typePiece(DEFAULT_TYPE_PIECE)
            .numeroPiece(DEFAULT_NUMERO_PIECE)
            .sexe(DEFAULT_SEXE)
            .regionResidence(DEFAULT_REGION_RESIDENCE)
            .depResidence(DEFAULT_DEP_RESIDENCE)
            .adresseResidence(DEFAULT_ADRESSE_RESIDENCE)
            .telephone1(DEFAULT_TELEPHONE_1)
            .telephone2(DEFAULT_TELEPHONE_2)
            .email(DEFAULT_EMAIL)
            .niveauFormation(DEFAULT_NIVEAU_FORMATION)
            .specialite(DEFAULT_SPECIALITE)
            .intituleDiplome(DEFAULT_INTITULE_DIPLOME)
            .diplome(DEFAULT_DIPLOME)
            .diplomeContentType(DEFAULT_DIPLOME_CONTENT_TYPE)
            .anneeObtention(DEFAULT_ANNEE_OBTENTION)
            .lieuObtention(DEFAULT_LIEU_OBTENTION)
            .cv(DEFAULT_CV)
            .cvContentType(DEFAULT_CV_CONTENT_TYPE)
            .lettreMotivation(DEFAULT_LETTRE_MOTIVATION)
            .lettreMotivationContentType(DEFAULT_LETTRE_MOTIVATION_CONTENT_TYPE)
            .profession(DEFAULT_PROFESSION)
            .autreSpecialite(DEFAULT_AUTRE_SPECIALITE)
            .nomCompetence(DEFAULT_NOM_COMPETENCE)
            .niveauCompetence(DEFAULT_NIVEAU_COMPETENCE)
            .intituleExperience(DEFAULT_INTITULE_EXPERIENCE)
            .posteOccupe(DEFAULT_POSTE_OCCUPE)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .nomEntreprise(DEFAULT_NOM_ENTREPRISE)
            .mission(DEFAULT_MISSION);
        return dossier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dossier createUpdatedEntity(EntityManager em) {
        Dossier dossier = new Dossier()
            .numDossier(UPDATED_NUM_DOSSIER)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .nomUtilisateur(UPDATED_NOM_UTILISATEUR)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .regionNaiss(UPDATED_REGION_NAISS)
            .departementNaiss(UPDATED_DEPARTEMENT_NAISS)
            .typePiece(UPDATED_TYPE_PIECE)
            .numeroPiece(UPDATED_NUMERO_PIECE)
            .sexe(UPDATED_SEXE)
            .regionResidence(UPDATED_REGION_RESIDENCE)
            .depResidence(UPDATED_DEP_RESIDENCE)
            .adresseResidence(UPDATED_ADRESSE_RESIDENCE)
            .telephone1(UPDATED_TELEPHONE_1)
            .telephone2(UPDATED_TELEPHONE_2)
            .email(UPDATED_EMAIL)
            .niveauFormation(UPDATED_NIVEAU_FORMATION)
            .specialite(UPDATED_SPECIALITE)
            .intituleDiplome(UPDATED_INTITULE_DIPLOME)
            .diplome(UPDATED_DIPLOME)
            .diplomeContentType(UPDATED_DIPLOME_CONTENT_TYPE)
            .anneeObtention(UPDATED_ANNEE_OBTENTION)
            .lieuObtention(UPDATED_LIEU_OBTENTION)
            .cv(UPDATED_CV)
            .cvContentType(UPDATED_CV_CONTENT_TYPE)
            .lettreMotivation(UPDATED_LETTRE_MOTIVATION)
            .lettreMotivationContentType(UPDATED_LETTRE_MOTIVATION_CONTENT_TYPE)
            .profession(UPDATED_PROFESSION)
            .autreSpecialite(UPDATED_AUTRE_SPECIALITE)
            .nomCompetence(UPDATED_NOM_COMPETENCE)
            .niveauCompetence(UPDATED_NIVEAU_COMPETENCE)
            .intituleExperience(UPDATED_INTITULE_EXPERIENCE)
            .posteOccupe(UPDATED_POSTE_OCCUPE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .nomEntreprise(UPDATED_NOM_ENTREPRISE)
            .mission(UPDATED_MISSION);
        return dossier;
    }

    @BeforeEach
    public void initTest() {
        dossier = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDossier != null) {
            dossierRepository.delete(insertedDossier);
            insertedDossier = null;
        }
    }

    @Test
    @Transactional
    void createDossier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Dossier
        var returnedDossier = om.readValue(
            restDossierMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossier)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Dossier.class
        );

        // Validate the Dossier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDossierUpdatableFieldsEquals(returnedDossier, getPersistedDossier(returnedDossier));

        insertedDossier = returnedDossier;
    }

    @Test
    @Transactional
    void createDossierWithExistingId() throws Exception {
        // Create the Dossier with an existing ID
        dossier.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossier)))
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dossier.setPrenom(null);

        // Create the Dossier, which fails.

        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossier)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dossier.setNom(null);

        // Create the Dossier, which fails.

        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossier)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPosteOccupeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dossier.setPosteOccupe(null);

        // Create the Dossier, which fails.

        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossier)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateDebutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dossier.setDateDebut(null);

        // Create the Dossier, which fails.

        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossier)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateFinIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dossier.setDateFin(null);

        // Create the Dossier, which fails.

        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossier)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomEntrepriseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dossier.setNomEntreprise(null);

        // Create the Dossier, which fails.

        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossier)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDossiers() throws Exception {
        // Initialize the database
        insertedDossier = dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList
        restDossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].numDossier").value(hasItem(DEFAULT_NUM_DOSSIER)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomUtilisateur").value(hasItem(DEFAULT_NOM_UTILISATEUR)))
            .andExpect(jsonPath("$.[*].dateNaiss").value(hasItem(DEFAULT_DATE_NAISS.toString())))
            .andExpect(jsonPath("$.[*].lieuNaiss").value(hasItem(DEFAULT_LIEU_NAISS)))
            .andExpect(jsonPath("$.[*].regionNaiss").value(hasItem(DEFAULT_REGION_NAISS.toString())))
            .andExpect(jsonPath("$.[*].departementNaiss").value(hasItem(DEFAULT_DEPARTEMENT_NAISS.toString())))
            .andExpect(jsonPath("$.[*].typePiece").value(hasItem(DEFAULT_TYPE_PIECE.toString())))
            .andExpect(jsonPath("$.[*].numeroPiece").value(hasItem(DEFAULT_NUMERO_PIECE.intValue())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].regionResidence").value(hasItem(DEFAULT_REGION_RESIDENCE.toString())))
            .andExpect(jsonPath("$.[*].depResidence").value(hasItem(DEFAULT_DEP_RESIDENCE.toString())))
            .andExpect(jsonPath("$.[*].adresseResidence").value(hasItem(DEFAULT_ADRESSE_RESIDENCE)))
            .andExpect(jsonPath("$.[*].telephone1").value(hasItem(DEFAULT_TELEPHONE_1)))
            .andExpect(jsonPath("$.[*].telephone2").value(hasItem(DEFAULT_TELEPHONE_2)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].niveauFormation").value(hasItem(DEFAULT_NIVEAU_FORMATION.toString())))
            .andExpect(jsonPath("$.[*].specialite").value(hasItem(DEFAULT_SPECIALITE.toString())))
            .andExpect(jsonPath("$.[*].intituleDiplome").value(hasItem(DEFAULT_INTITULE_DIPLOME)))
            .andExpect(jsonPath("$.[*].diplomeContentType").value(hasItem(DEFAULT_DIPLOME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].diplome").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_DIPLOME))))
            .andExpect(jsonPath("$.[*].anneeObtention").value(hasItem(DEFAULT_ANNEE_OBTENTION.toString())))
            .andExpect(jsonPath("$.[*].lieuObtention").value(hasItem(DEFAULT_LIEU_OBTENTION)))
            .andExpect(jsonPath("$.[*].cvContentType").value(hasItem(DEFAULT_CV_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cv").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_CV))))
            .andExpect(jsonPath("$.[*].lettreMotivationContentType").value(hasItem(DEFAULT_LETTRE_MOTIVATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].lettreMotivation").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_LETTRE_MOTIVATION))))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].autreSpecialite").value(hasItem(DEFAULT_AUTRE_SPECIALITE)))
            .andExpect(jsonPath("$.[*].nomCompetence").value(hasItem(DEFAULT_NOM_COMPETENCE)))
            .andExpect(jsonPath("$.[*].niveauCompetence").value(hasItem(DEFAULT_NIVEAU_COMPETENCE.toString())))
            .andExpect(jsonPath("$.[*].intituleExperience").value(hasItem(DEFAULT_INTITULE_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].posteOccupe").value(hasItem(DEFAULT_POSTE_OCCUPE)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].nomEntreprise").value(hasItem(DEFAULT_NOM_ENTREPRISE)))
            .andExpect(jsonPath("$.[*].mission").value(hasItem(DEFAULT_MISSION.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDossiersWithEagerRelationshipsIsEnabled() throws Exception {
        when(dossierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDossierMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dossierServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDossiersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dossierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDossierMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(dossierRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDossier() throws Exception {
        // Initialize the database
        insertedDossier = dossierRepository.saveAndFlush(dossier);

        // Get the dossier
        restDossierMockMvc
            .perform(get(ENTITY_API_URL_ID, dossier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dossier.getId().intValue()))
            .andExpect(jsonPath("$.numDossier").value(DEFAULT_NUM_DOSSIER))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomUtilisateur").value(DEFAULT_NOM_UTILISATEUR))
            .andExpect(jsonPath("$.dateNaiss").value(DEFAULT_DATE_NAISS.toString()))
            .andExpect(jsonPath("$.lieuNaiss").value(DEFAULT_LIEU_NAISS))
            .andExpect(jsonPath("$.regionNaiss").value(DEFAULT_REGION_NAISS.toString()))
            .andExpect(jsonPath("$.departementNaiss").value(DEFAULT_DEPARTEMENT_NAISS.toString()))
            .andExpect(jsonPath("$.typePiece").value(DEFAULT_TYPE_PIECE.toString()))
            .andExpect(jsonPath("$.numeroPiece").value(DEFAULT_NUMERO_PIECE.intValue()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.regionResidence").value(DEFAULT_REGION_RESIDENCE.toString()))
            .andExpect(jsonPath("$.depResidence").value(DEFAULT_DEP_RESIDENCE.toString()))
            .andExpect(jsonPath("$.adresseResidence").value(DEFAULT_ADRESSE_RESIDENCE))
            .andExpect(jsonPath("$.telephone1").value(DEFAULT_TELEPHONE_1))
            .andExpect(jsonPath("$.telephone2").value(DEFAULT_TELEPHONE_2))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.niveauFormation").value(DEFAULT_NIVEAU_FORMATION.toString()))
            .andExpect(jsonPath("$.specialite").value(DEFAULT_SPECIALITE.toString()))
            .andExpect(jsonPath("$.intituleDiplome").value(DEFAULT_INTITULE_DIPLOME))
            .andExpect(jsonPath("$.diplomeContentType").value(DEFAULT_DIPLOME_CONTENT_TYPE))
            .andExpect(jsonPath("$.diplome").value(Base64.getEncoder().encodeToString(DEFAULT_DIPLOME)))
            .andExpect(jsonPath("$.anneeObtention").value(DEFAULT_ANNEE_OBTENTION.toString()))
            .andExpect(jsonPath("$.lieuObtention").value(DEFAULT_LIEU_OBTENTION))
            .andExpect(jsonPath("$.cvContentType").value(DEFAULT_CV_CONTENT_TYPE))
            .andExpect(jsonPath("$.cv").value(Base64.getEncoder().encodeToString(DEFAULT_CV)))
            .andExpect(jsonPath("$.lettreMotivationContentType").value(DEFAULT_LETTRE_MOTIVATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.lettreMotivation").value(Base64.getEncoder().encodeToString(DEFAULT_LETTRE_MOTIVATION)))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION))
            .andExpect(jsonPath("$.autreSpecialite").value(DEFAULT_AUTRE_SPECIALITE))
            .andExpect(jsonPath("$.nomCompetence").value(DEFAULT_NOM_COMPETENCE))
            .andExpect(jsonPath("$.niveauCompetence").value(DEFAULT_NIVEAU_COMPETENCE.toString()))
            .andExpect(jsonPath("$.intituleExperience").value(DEFAULT_INTITULE_EXPERIENCE))
            .andExpect(jsonPath("$.posteOccupe").value(DEFAULT_POSTE_OCCUPE))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.nomEntreprise").value(DEFAULT_NOM_ENTREPRISE))
            .andExpect(jsonPath("$.mission").value(DEFAULT_MISSION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDossier() throws Exception {
        // Get the dossier
        restDossierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDossier() throws Exception {
        // Initialize the database
        insertedDossier = dossierRepository.saveAndFlush(dossier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dossier
        Dossier updatedDossier = dossierRepository.findById(dossier.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDossier are not directly saved in db
        em.detach(updatedDossier);
        updatedDossier
            .numDossier(UPDATED_NUM_DOSSIER)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .nomUtilisateur(UPDATED_NOM_UTILISATEUR)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .regionNaiss(UPDATED_REGION_NAISS)
            .departementNaiss(UPDATED_DEPARTEMENT_NAISS)
            .typePiece(UPDATED_TYPE_PIECE)
            .numeroPiece(UPDATED_NUMERO_PIECE)
            .sexe(UPDATED_SEXE)
            .regionResidence(UPDATED_REGION_RESIDENCE)
            .depResidence(UPDATED_DEP_RESIDENCE)
            .adresseResidence(UPDATED_ADRESSE_RESIDENCE)
            .telephone1(UPDATED_TELEPHONE_1)
            .telephone2(UPDATED_TELEPHONE_2)
            .email(UPDATED_EMAIL)
            .niveauFormation(UPDATED_NIVEAU_FORMATION)
            .specialite(UPDATED_SPECIALITE)
            .intituleDiplome(UPDATED_INTITULE_DIPLOME)
            .diplome(UPDATED_DIPLOME)
            .diplomeContentType(UPDATED_DIPLOME_CONTENT_TYPE)
            .anneeObtention(UPDATED_ANNEE_OBTENTION)
            .lieuObtention(UPDATED_LIEU_OBTENTION)
            .cv(UPDATED_CV)
            .cvContentType(UPDATED_CV_CONTENT_TYPE)
            .lettreMotivation(UPDATED_LETTRE_MOTIVATION)
            .lettreMotivationContentType(UPDATED_LETTRE_MOTIVATION_CONTENT_TYPE)
            .profession(UPDATED_PROFESSION)
            .autreSpecialite(UPDATED_AUTRE_SPECIALITE)
            .nomCompetence(UPDATED_NOM_COMPETENCE)
            .niveauCompetence(UPDATED_NIVEAU_COMPETENCE)
            .intituleExperience(UPDATED_INTITULE_EXPERIENCE)
            .posteOccupe(UPDATED_POSTE_OCCUPE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .nomEntreprise(UPDATED_NOM_ENTREPRISE)
            .mission(UPDATED_MISSION);

        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDossier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDossier))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDossierToMatchAllProperties(updatedDossier);
    }

    @Test
    @Transactional
    void putNonExistingDossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dossier.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(put(ENTITY_API_URL_ID, dossier.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossier)))
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dossier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dossier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDossierWithPatch() throws Exception {
        // Initialize the database
        insertedDossier = dossierRepository.saveAndFlush(dossier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dossier using partial update
        Dossier partialUpdatedDossier = new Dossier();
        partialUpdatedDossier.setId(dossier.getId());

        partialUpdatedDossier
            .nomUtilisateur(UPDATED_NOM_UTILISATEUR)
            .regionNaiss(UPDATED_REGION_NAISS)
            .departementNaiss(UPDATED_DEPARTEMENT_NAISS)
            .sexe(UPDATED_SEXE)
            .regionResidence(UPDATED_REGION_RESIDENCE)
            .adresseResidence(UPDATED_ADRESSE_RESIDENCE)
            .telephone1(UPDATED_TELEPHONE_1)
            .telephone2(UPDATED_TELEPHONE_2)
            .intituleDiplome(UPDATED_INTITULE_DIPLOME)
            .anneeObtention(UPDATED_ANNEE_OBTENTION)
            .cv(UPDATED_CV)
            .cvContentType(UPDATED_CV_CONTENT_TYPE)
            .lettreMotivation(UPDATED_LETTRE_MOTIVATION)
            .lettreMotivationContentType(UPDATED_LETTRE_MOTIVATION_CONTENT_TYPE)
            .profession(UPDATED_PROFESSION)
            .niveauCompetence(UPDATED_NIVEAU_COMPETENCE)
            .intituleExperience(UPDATED_INTITULE_EXPERIENCE)
            .mission(UPDATED_MISSION);

        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDossier))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDossierUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDossier, dossier), getPersistedDossier(dossier));
    }

    @Test
    @Transactional
    void fullUpdateDossierWithPatch() throws Exception {
        // Initialize the database
        insertedDossier = dossierRepository.saveAndFlush(dossier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dossier using partial update
        Dossier partialUpdatedDossier = new Dossier();
        partialUpdatedDossier.setId(dossier.getId());

        partialUpdatedDossier
            .numDossier(UPDATED_NUM_DOSSIER)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .nomUtilisateur(UPDATED_NOM_UTILISATEUR)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .regionNaiss(UPDATED_REGION_NAISS)
            .departementNaiss(UPDATED_DEPARTEMENT_NAISS)
            .typePiece(UPDATED_TYPE_PIECE)
            .numeroPiece(UPDATED_NUMERO_PIECE)
            .sexe(UPDATED_SEXE)
            .regionResidence(UPDATED_REGION_RESIDENCE)
            .depResidence(UPDATED_DEP_RESIDENCE)
            .adresseResidence(UPDATED_ADRESSE_RESIDENCE)
            .telephone1(UPDATED_TELEPHONE_1)
            .telephone2(UPDATED_TELEPHONE_2)
            .email(UPDATED_EMAIL)
            .niveauFormation(UPDATED_NIVEAU_FORMATION)
            .specialite(UPDATED_SPECIALITE)
            .intituleDiplome(UPDATED_INTITULE_DIPLOME)
            .diplome(UPDATED_DIPLOME)
            .diplomeContentType(UPDATED_DIPLOME_CONTENT_TYPE)
            .anneeObtention(UPDATED_ANNEE_OBTENTION)
            .lieuObtention(UPDATED_LIEU_OBTENTION)
            .cv(UPDATED_CV)
            .cvContentType(UPDATED_CV_CONTENT_TYPE)
            .lettreMotivation(UPDATED_LETTRE_MOTIVATION)
            .lettreMotivationContentType(UPDATED_LETTRE_MOTIVATION_CONTENT_TYPE)
            .profession(UPDATED_PROFESSION)
            .autreSpecialite(UPDATED_AUTRE_SPECIALITE)
            .nomCompetence(UPDATED_NOM_COMPETENCE)
            .niveauCompetence(UPDATED_NIVEAU_COMPETENCE)
            .intituleExperience(UPDATED_INTITULE_EXPERIENCE)
            .posteOccupe(UPDATED_POSTE_OCCUPE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .nomEntreprise(UPDATED_NOM_ENTREPRISE)
            .mission(UPDATED_MISSION);

        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDossier))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDossierUpdatableFieldsEquals(partialUpdatedDossier, getPersistedDossier(partialUpdatedDossier));
    }

    @Test
    @Transactional
    void patchNonExistingDossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dossier.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dossier.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dossier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dossier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dossier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDossier() throws Exception {
        // Initialize the database
        insertedDossier = dossierRepository.saveAndFlush(dossier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the dossier
        restDossierMockMvc
            .perform(delete(ENTITY_API_URL_ID, dossier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return dossierRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Dossier getPersistedDossier(Dossier dossier) {
        return dossierRepository.findById(dossier.getId()).orElseThrow();
    }

    protected void assertPersistedDossierToMatchAllProperties(Dossier expectedDossier) {
        assertDossierAllPropertiesEquals(expectedDossier, getPersistedDossier(expectedDossier));
    }

    protected void assertPersistedDossierToMatchUpdatableProperties(Dossier expectedDossier) {
        assertDossierAllUpdatablePropertiesEquals(expectedDossier, getPersistedDossier(expectedDossier));
    }
}
