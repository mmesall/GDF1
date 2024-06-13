package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.FormationInitialeAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.FormationInitiale;
import com.mycompany.myapp.domain.enumeration.Admission;
import com.mycompany.myapp.domain.enumeration.CFP;
import com.mycompany.myapp.domain.enumeration.DiplomeObtenu;
import com.mycompany.myapp.domain.enumeration.DiplomeRequis;
import com.mycompany.myapp.domain.enumeration.LYCEE;
import com.mycompany.myapp.domain.enumeration.NiveauEtude;
import com.mycompany.myapp.domain.enumeration.NomFiliere;
import com.mycompany.myapp.domain.enumeration.NomSerie;
import com.mycompany.myapp.repository.FormationInitialeRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FormationInitialeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormationInitialeResourceIT {

    private static final String DEFAULT_NOM_FORMATION_I = "AAAAAAAAAA";
    private static final String UPDATED_NOM_FORMATION_I = "BBBBBBBBBB";

    private static final String DEFAULT_DUREE = "AAAAAAAAAA";
    private static final String UPDATED_DUREE = "BBBBBBBBBB";

    private static final Admission DEFAULT_ADMISSION = Admission.CONCOURS;
    private static final Admission UPDATED_ADMISSION = Admission.PC;

    private static final DiplomeRequis DEFAULT_DIPLOME_REQUIS = DiplomeRequis.ATTESTATION;
    private static final DiplomeRequis UPDATED_DIPLOME_REQUIS = DiplomeRequis.CAP;

    private static final NiveauEtude DEFAULT_NIVEAU_ETUDE = NiveauEtude.CINQUIEME;
    private static final NiveauEtude UPDATED_NIVEAU_ETUDE = NiveauEtude.QUATRIEME;

    private static final byte[] DEFAULT_FICHE_FORMATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHE_FORMATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHE_FORMATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHE_FORMATION_CONTENT_TYPE = "image/png";

    private static final NomFiliere DEFAULT_FILIERE = NomFiliere.AGRI_ELEVAGE;
    private static final NomFiliere UPDATED_FILIERE = NomFiliere.AGRICULTURE;

    private static final NomSerie DEFAULT_SERIE = NomSerie.STEG;
    private static final NomSerie UPDATED_SERIE = NomSerie.STIDD_M;

    private static final CFP DEFAULT_CFP = CFP.CEDT_G15;
    private static final CFP UPDATED_CFP = CFP.CFP_OUAKAM;

    private static final LYCEE DEFAULT_LYCEE = LYCEE.LTID_DAKAR;
    private static final LYCEE UPDATED_LYCEE = LYCEE.LTCD_DAKAR;

    private static final String DEFAULT_NOM_CONCOURS = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CONCOURS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OUVERTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OUVERTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CLOTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CLOTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CONCOURS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CONCOURS = LocalDate.now(ZoneId.systemDefault());

    private static final DiplomeObtenu DEFAULT_NOM_DIPLOME = DiplomeObtenu.CPS;
    private static final DiplomeObtenu UPDATED_NOM_DIPLOME = DiplomeObtenu.CAP;

    private static final String DEFAULT_NOM_DEBOUCHE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_DEBOUCHE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/formation-initiales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FormationInitialeRepository formationInitialeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormationInitialeMockMvc;

    private FormationInitiale formationInitiale;

    private FormationInitiale insertedFormationInitiale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormationInitiale createEntity(EntityManager em) {
        FormationInitiale formationInitiale = new FormationInitiale()
            .nomFormationI(DEFAULT_NOM_FORMATION_I)
            .duree(DEFAULT_DUREE)
            .admission(DEFAULT_ADMISSION)
            .diplomeRequis(DEFAULT_DIPLOME_REQUIS)
            .niveauEtude(DEFAULT_NIVEAU_ETUDE)
            .ficheFormation(DEFAULT_FICHE_FORMATION)
            .ficheFormationContentType(DEFAULT_FICHE_FORMATION_CONTENT_TYPE)
            .filiere(DEFAULT_FILIERE)
            .serie(DEFAULT_SERIE)
            .cfp(DEFAULT_CFP)
            .lycee(DEFAULT_LYCEE)
            .nomConcours(DEFAULT_NOM_CONCOURS)
            .dateOuverture(DEFAULT_DATE_OUVERTURE)
            .dateCloture(DEFAULT_DATE_CLOTURE)
            .dateConcours(DEFAULT_DATE_CONCOURS)
            .nomDiplome(DEFAULT_NOM_DIPLOME)
            .nomDebouche(DEFAULT_NOM_DEBOUCHE);
        return formationInitiale;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormationInitiale createUpdatedEntity(EntityManager em) {
        FormationInitiale formationInitiale = new FormationInitiale()
            .nomFormationI(UPDATED_NOM_FORMATION_I)
            .duree(UPDATED_DUREE)
            .admission(UPDATED_ADMISSION)
            .diplomeRequis(UPDATED_DIPLOME_REQUIS)
            .niveauEtude(UPDATED_NIVEAU_ETUDE)
            .ficheFormation(UPDATED_FICHE_FORMATION)
            .ficheFormationContentType(UPDATED_FICHE_FORMATION_CONTENT_TYPE)
            .filiere(UPDATED_FILIERE)
            .serie(UPDATED_SERIE)
            .cfp(UPDATED_CFP)
            .lycee(UPDATED_LYCEE)
            .nomConcours(UPDATED_NOM_CONCOURS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .dateConcours(UPDATED_DATE_CONCOURS)
            .nomDiplome(UPDATED_NOM_DIPLOME)
            .nomDebouche(UPDATED_NOM_DEBOUCHE);
        return formationInitiale;
    }

    @BeforeEach
    public void initTest() {
        formationInitiale = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFormationInitiale != null) {
            formationInitialeRepository.delete(insertedFormationInitiale);
            insertedFormationInitiale = null;
        }
    }

    @Test
    @Transactional
    void createFormationInitiale() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FormationInitiale
        var returnedFormationInitiale = om.readValue(
            restFormationInitialeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formationInitiale)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FormationInitiale.class
        );

        // Validate the FormationInitiale in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFormationInitialeUpdatableFieldsEquals(returnedFormationInitiale, getPersistedFormationInitiale(returnedFormationInitiale));

        insertedFormationInitiale = returnedFormationInitiale;
    }

    @Test
    @Transactional
    void createFormationInitialeWithExistingId() throws Exception {
        // Create the FormationInitiale with an existing ID
        formationInitiale.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormationInitialeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formationInitiale)))
            .andExpect(status().isBadRequest());

        // Validate the FormationInitiale in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormationInitiales() throws Exception {
        // Initialize the database
        insertedFormationInitiale = formationInitialeRepository.saveAndFlush(formationInitiale);

        // Get all the formationInitialeList
        restFormationInitialeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formationInitiale.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomFormationI").value(hasItem(DEFAULT_NOM_FORMATION_I)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].admission").value(hasItem(DEFAULT_ADMISSION.toString())))
            .andExpect(jsonPath("$.[*].diplomeRequis").value(hasItem(DEFAULT_DIPLOME_REQUIS.toString())))
            .andExpect(jsonPath("$.[*].niveauEtude").value(hasItem(DEFAULT_NIVEAU_ETUDE.toString())))
            .andExpect(jsonPath("$.[*].ficheFormationContentType").value(hasItem(DEFAULT_FICHE_FORMATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].ficheFormation").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_FICHE_FORMATION))))
            .andExpect(jsonPath("$.[*].filiere").value(hasItem(DEFAULT_FILIERE.toString())))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE.toString())))
            .andExpect(jsonPath("$.[*].cfp").value(hasItem(DEFAULT_CFP.toString())))
            .andExpect(jsonPath("$.[*].lycee").value(hasItem(DEFAULT_LYCEE.toString())))
            .andExpect(jsonPath("$.[*].nomConcours").value(hasItem(DEFAULT_NOM_CONCOURS)))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].dateCloture").value(hasItem(DEFAULT_DATE_CLOTURE.toString())))
            .andExpect(jsonPath("$.[*].dateConcours").value(hasItem(DEFAULT_DATE_CONCOURS.toString())))
            .andExpect(jsonPath("$.[*].nomDiplome").value(hasItem(DEFAULT_NOM_DIPLOME.toString())))
            .andExpect(jsonPath("$.[*].nomDebouche").value(hasItem(DEFAULT_NOM_DEBOUCHE)));
    }

    @Test
    @Transactional
    void getFormationInitiale() throws Exception {
        // Initialize the database
        insertedFormationInitiale = formationInitialeRepository.saveAndFlush(formationInitiale);

        // Get the formationInitiale
        restFormationInitialeMockMvc
            .perform(get(ENTITY_API_URL_ID, formationInitiale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formationInitiale.getId().intValue()))
            .andExpect(jsonPath("$.nomFormationI").value(DEFAULT_NOM_FORMATION_I))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE))
            .andExpect(jsonPath("$.admission").value(DEFAULT_ADMISSION.toString()))
            .andExpect(jsonPath("$.diplomeRequis").value(DEFAULT_DIPLOME_REQUIS.toString()))
            .andExpect(jsonPath("$.niveauEtude").value(DEFAULT_NIVEAU_ETUDE.toString()))
            .andExpect(jsonPath("$.ficheFormationContentType").value(DEFAULT_FICHE_FORMATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.ficheFormation").value(Base64.getEncoder().encodeToString(DEFAULT_FICHE_FORMATION)))
            .andExpect(jsonPath("$.filiere").value(DEFAULT_FILIERE.toString()))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE.toString()))
            .andExpect(jsonPath("$.cfp").value(DEFAULT_CFP.toString()))
            .andExpect(jsonPath("$.lycee").value(DEFAULT_LYCEE.toString()))
            .andExpect(jsonPath("$.nomConcours").value(DEFAULT_NOM_CONCOURS))
            .andExpect(jsonPath("$.dateOuverture").value(DEFAULT_DATE_OUVERTURE.toString()))
            .andExpect(jsonPath("$.dateCloture").value(DEFAULT_DATE_CLOTURE.toString()))
            .andExpect(jsonPath("$.dateConcours").value(DEFAULT_DATE_CONCOURS.toString()))
            .andExpect(jsonPath("$.nomDiplome").value(DEFAULT_NOM_DIPLOME.toString()))
            .andExpect(jsonPath("$.nomDebouche").value(DEFAULT_NOM_DEBOUCHE));
    }

    @Test
    @Transactional
    void getNonExistingFormationInitiale() throws Exception {
        // Get the formationInitiale
        restFormationInitialeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFormationInitiale() throws Exception {
        // Initialize the database
        insertedFormationInitiale = formationInitialeRepository.saveAndFlush(formationInitiale);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formationInitiale
        FormationInitiale updatedFormationInitiale = formationInitialeRepository.findById(formationInitiale.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFormationInitiale are not directly saved in db
        em.detach(updatedFormationInitiale);
        updatedFormationInitiale
            .nomFormationI(UPDATED_NOM_FORMATION_I)
            .duree(UPDATED_DUREE)
            .admission(UPDATED_ADMISSION)
            .diplomeRequis(UPDATED_DIPLOME_REQUIS)
            .niveauEtude(UPDATED_NIVEAU_ETUDE)
            .ficheFormation(UPDATED_FICHE_FORMATION)
            .ficheFormationContentType(UPDATED_FICHE_FORMATION_CONTENT_TYPE)
            .filiere(UPDATED_FILIERE)
            .serie(UPDATED_SERIE)
            .cfp(UPDATED_CFP)
            .lycee(UPDATED_LYCEE)
            .nomConcours(UPDATED_NOM_CONCOURS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .dateConcours(UPDATED_DATE_CONCOURS)
            .nomDiplome(UPDATED_NOM_DIPLOME)
            .nomDebouche(UPDATED_NOM_DEBOUCHE);

        restFormationInitialeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormationInitiale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFormationInitiale))
            )
            .andExpect(status().isOk());

        // Validate the FormationInitiale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFormationInitialeToMatchAllProperties(updatedFormationInitiale);
    }

    @Test
    @Transactional
    void putNonExistingFormationInitiale() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formationInitiale.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormationInitialeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formationInitiale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formationInitiale))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormationInitiale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormationInitiale() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formationInitiale.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationInitialeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formationInitiale))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormationInitiale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormationInitiale() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formationInitiale.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationInitialeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formationInitiale)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormationInitiale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormationInitialeWithPatch() throws Exception {
        // Initialize the database
        insertedFormationInitiale = formationInitialeRepository.saveAndFlush(formationInitiale);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formationInitiale using partial update
        FormationInitiale partialUpdatedFormationInitiale = new FormationInitiale();
        partialUpdatedFormationInitiale.setId(formationInitiale.getId());

        partialUpdatedFormationInitiale
            .nomFormationI(UPDATED_NOM_FORMATION_I)
            .ficheFormation(UPDATED_FICHE_FORMATION)
            .ficheFormationContentType(UPDATED_FICHE_FORMATION_CONTENT_TYPE)
            .serie(UPDATED_SERIE)
            .lycee(UPDATED_LYCEE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .nomDebouche(UPDATED_NOM_DEBOUCHE);

        restFormationInitialeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormationInitiale.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormationInitiale))
            )
            .andExpect(status().isOk());

        // Validate the FormationInitiale in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormationInitialeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFormationInitiale, formationInitiale),
            getPersistedFormationInitiale(formationInitiale)
        );
    }

    @Test
    @Transactional
    void fullUpdateFormationInitialeWithPatch() throws Exception {
        // Initialize the database
        insertedFormationInitiale = formationInitialeRepository.saveAndFlush(formationInitiale);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formationInitiale using partial update
        FormationInitiale partialUpdatedFormationInitiale = new FormationInitiale();
        partialUpdatedFormationInitiale.setId(formationInitiale.getId());

        partialUpdatedFormationInitiale
            .nomFormationI(UPDATED_NOM_FORMATION_I)
            .duree(UPDATED_DUREE)
            .admission(UPDATED_ADMISSION)
            .diplomeRequis(UPDATED_DIPLOME_REQUIS)
            .niveauEtude(UPDATED_NIVEAU_ETUDE)
            .ficheFormation(UPDATED_FICHE_FORMATION)
            .ficheFormationContentType(UPDATED_FICHE_FORMATION_CONTENT_TYPE)
            .filiere(UPDATED_FILIERE)
            .serie(UPDATED_SERIE)
            .cfp(UPDATED_CFP)
            .lycee(UPDATED_LYCEE)
            .nomConcours(UPDATED_NOM_CONCOURS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .dateConcours(UPDATED_DATE_CONCOURS)
            .nomDiplome(UPDATED_NOM_DIPLOME)
            .nomDebouche(UPDATED_NOM_DEBOUCHE);

        restFormationInitialeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormationInitiale.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormationInitiale))
            )
            .andExpect(status().isOk());

        // Validate the FormationInitiale in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormationInitialeUpdatableFieldsEquals(
            partialUpdatedFormationInitiale,
            getPersistedFormationInitiale(partialUpdatedFormationInitiale)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFormationInitiale() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formationInitiale.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormationInitialeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formationInitiale.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formationInitiale))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormationInitiale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormationInitiale() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formationInitiale.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationInitialeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formationInitiale))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormationInitiale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormationInitiale() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formationInitiale.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationInitialeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(formationInitiale)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormationInitiale in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormationInitiale() throws Exception {
        // Initialize the database
        insertedFormationInitiale = formationInitialeRepository.saveAndFlush(formationInitiale);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the formationInitiale
        restFormationInitialeMockMvc
            .perform(delete(ENTITY_API_URL_ID, formationInitiale.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return formationInitialeRepository.count();
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

    protected FormationInitiale getPersistedFormationInitiale(FormationInitiale formationInitiale) {
        return formationInitialeRepository.findById(formationInitiale.getId()).orElseThrow();
    }

    protected void assertPersistedFormationInitialeToMatchAllProperties(FormationInitiale expectedFormationInitiale) {
        assertFormationInitialeAllPropertiesEquals(expectedFormationInitiale, getPersistedFormationInitiale(expectedFormationInitiale));
    }

    protected void assertPersistedFormationInitialeToMatchUpdatableProperties(FormationInitiale expectedFormationInitiale) {
        assertFormationInitialeAllUpdatablePropertiesEquals(
            expectedFormationInitiale,
            getPersistedFormationInitiale(expectedFormationInitiale)
        );
    }
}
