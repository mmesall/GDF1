package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.EtablissementAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Etablissement;
import com.mycompany.myapp.domain.enumeration.CFP;
import com.mycompany.myapp.domain.enumeration.LYCEE;
import com.mycompany.myapp.domain.enumeration.NomDepartement;
import com.mycompany.myapp.domain.enumeration.NomEtablissement;
import com.mycompany.myapp.domain.enumeration.NomFiliere;
import com.mycompany.myapp.domain.enumeration.NomRegion;
import com.mycompany.myapp.domain.enumeration.NomSerie;
import com.mycompany.myapp.domain.enumeration.StatutEtab;
import com.mycompany.myapp.domain.enumeration.TypeEtablissement;
import com.mycompany.myapp.repository.EtablissementRepository;
import jakarta.persistence.EntityManager;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EtablissementResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class EtablissementResourceIT {

    private static final NomEtablissement DEFAULT_NOM_ETABLISSEMENT = NomEtablissement.CEDT_G15;
    private static final NomEtablissement UPDATED_NOM_ETABLISSEMENT = NomEtablissement.CFP_OUAKAM;

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final NomRegion DEFAULT_REGION = NomRegion.DAKAR;
    private static final NomRegion UPDATED_REGION = NomRegion.DIOURBEL;

    private static final NomDepartement DEFAULT_DEPARTEMENT = NomDepartement.DAKAR;
    private static final NomDepartement UPDATED_DEPARTEMENT = NomDepartement.GUEDIAWAYE;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_TELEPHONE = 1L;
    private static final Long UPDATED_TELEPHONE = 2L;

    private static final TypeEtablissement DEFAULT_TYPE_ETABLISSEMENT = TypeEtablissement.CFP;
    private static final TypeEtablissement UPDATED_TYPE_ETABLISSEMENT = TypeEtablissement.LYCEE_TECH;

    private static final StatutEtab DEFAULT_STATUT = StatutEtab.PRIVE;
    private static final StatutEtab UPDATED_STATUT = StatutEtab.PUBLIC;

    private static final String DEFAULT_AUTRE_REGION = "AAAAAAAAAA";
    private static final String UPDATED_AUTRE_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_AUTRE_DEPARTEMENT = "AAAAAAAAAA";
    private static final String UPDATED_AUTRE_DEPARTEMENT = "BBBBBBBBBB";

    private static final CFP DEFAULT_CFP = CFP.CEDT_G15;
    private static final CFP UPDATED_CFP = CFP.CFP_OUAKAM;

    private static final LYCEE DEFAULT_LYCEE = LYCEE.LTID_DAKAR;
    private static final LYCEE UPDATED_LYCEE = LYCEE.LTCD_DAKAR;

    private static final NomFiliere DEFAULT_FILIERE = NomFiliere.AGRI_ELEVAGE;
    private static final NomFiliere UPDATED_FILIERE = NomFiliere.AGRICULTURE;

    private static final NomSerie DEFAULT_SERIE = NomSerie.STEG;
    private static final NomSerie UPDATED_SERIE = NomSerie.STIDD_M;

    private static final String DEFAULT_AUTRE_FILIERE = "AAAAAAAAAA";
    private static final String UPDATED_AUTRE_FILIERE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTRE_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_AUTRE_SERIE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTRE_NOM_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_AUTRE_NOM_ETABLISSEMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/etablissements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EtablissementRepository etablissementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtablissementMockMvc;

    private Etablissement etablissement;

    private Etablissement insertedEtablissement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etablissement createEntity(EntityManager em) {
        Etablissement etablissement = new Etablissement()
            .nomEtablissement(DEFAULT_NOM_ETABLISSEMENT)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .region(DEFAULT_REGION)
            .departement(DEFAULT_DEPARTEMENT)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .typeEtablissement(DEFAULT_TYPE_ETABLISSEMENT)
            .statut(DEFAULT_STATUT)
            .autreRegion(DEFAULT_AUTRE_REGION)
            .autreDepartement(DEFAULT_AUTRE_DEPARTEMENT)
            .cfp(DEFAULT_CFP)
            .lycee(DEFAULT_LYCEE)
            .filiere(DEFAULT_FILIERE)
            .serie(DEFAULT_SERIE)
            .autreFiliere(DEFAULT_AUTRE_FILIERE)
            .autreSerie(DEFAULT_AUTRE_SERIE)
            .autreNomEtablissement(DEFAULT_AUTRE_NOM_ETABLISSEMENT);
        return etablissement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etablissement createUpdatedEntity(EntityManager em) {
        Etablissement etablissement = new Etablissement()
            .nomEtablissement(UPDATED_NOM_ETABLISSEMENT)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .region(UPDATED_REGION)
            .departement(UPDATED_DEPARTEMENT)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .typeEtablissement(UPDATED_TYPE_ETABLISSEMENT)
            .statut(UPDATED_STATUT)
            .autreRegion(UPDATED_AUTRE_REGION)
            .autreDepartement(UPDATED_AUTRE_DEPARTEMENT)
            .cfp(UPDATED_CFP)
            .lycee(UPDATED_LYCEE)
            .filiere(UPDATED_FILIERE)
            .serie(UPDATED_SERIE)
            .autreFiliere(UPDATED_AUTRE_FILIERE)
            .autreSerie(UPDATED_AUTRE_SERIE)
            .autreNomEtablissement(UPDATED_AUTRE_NOM_ETABLISSEMENT);
        return etablissement;
    }

    @BeforeEach
    public void initTest() {
        etablissement = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEtablissement != null) {
            etablissementRepository.delete(insertedEtablissement);
            insertedEtablissement = null;
        }
    }

    @Test
    @Transactional
    void createEtablissement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Etablissement
        var returnedEtablissement = om.readValue(
            restEtablissementMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etablissement)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Etablissement.class
        );

        // Validate the Etablissement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEtablissementUpdatableFieldsEquals(returnedEtablissement, getPersistedEtablissement(returnedEtablissement));

        insertedEtablissement = returnedEtablissement;
    }

    @Test
    @Transactional
    void createEtablissementWithExistingId() throws Exception {
        // Create the Etablissement with an existing ID
        etablissement.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etablissement)))
            .andExpect(status().isBadRequest());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRegionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        etablissement.setRegion(null);

        // Create the Etablissement, which fails.

        restEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etablissement)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepartementIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        etablissement.setDepartement(null);

        // Create the Etablissement, which fails.

        restEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etablissement)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        etablissement.setStatut(null);

        // Create the Etablissement, which fails.

        restEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etablissement)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEtablissements() throws Exception {
        // Initialize the database
        insertedEtablissement = etablissementRepository.saveAndFlush(etablissement);

        // Get all the etablissementList
        restEtablissementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etablissement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomEtablissement").value(hasItem(DEFAULT_NOM_ETABLISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].departement").value(hasItem(DEFAULT_DEPARTEMENT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.intValue())))
            .andExpect(jsonPath("$.[*].typeEtablissement").value(hasItem(DEFAULT_TYPE_ETABLISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].autreRegion").value(hasItem(DEFAULT_AUTRE_REGION)))
            .andExpect(jsonPath("$.[*].autreDepartement").value(hasItem(DEFAULT_AUTRE_DEPARTEMENT)))
            .andExpect(jsonPath("$.[*].cfp").value(hasItem(DEFAULT_CFP.toString())))
            .andExpect(jsonPath("$.[*].lycee").value(hasItem(DEFAULT_LYCEE.toString())))
            .andExpect(jsonPath("$.[*].filiere").value(hasItem(DEFAULT_FILIERE.toString())))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE.toString())))
            .andExpect(jsonPath("$.[*].autreFiliere").value(hasItem(DEFAULT_AUTRE_FILIERE)))
            .andExpect(jsonPath("$.[*].autreSerie").value(hasItem(DEFAULT_AUTRE_SERIE)))
            .andExpect(jsonPath("$.[*].autreNomEtablissement").value(hasItem(DEFAULT_AUTRE_NOM_ETABLISSEMENT)));
    }

    @Test
    @Transactional
    void getEtablissement() throws Exception {
        // Initialize the database
        insertedEtablissement = etablissementRepository.saveAndFlush(etablissement);

        // Get the etablissement
        restEtablissementMockMvc
            .perform(get(ENTITY_API_URL_ID, etablissement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etablissement.getId().intValue()))
            .andExpect(jsonPath("$.nomEtablissement").value(DEFAULT_NOM_ETABLISSEMENT.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64.getEncoder().encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.departement").value(DEFAULT_DEPARTEMENT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.intValue()))
            .andExpect(jsonPath("$.typeEtablissement").value(DEFAULT_TYPE_ETABLISSEMENT.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.autreRegion").value(DEFAULT_AUTRE_REGION))
            .andExpect(jsonPath("$.autreDepartement").value(DEFAULT_AUTRE_DEPARTEMENT))
            .andExpect(jsonPath("$.cfp").value(DEFAULT_CFP.toString()))
            .andExpect(jsonPath("$.lycee").value(DEFAULT_LYCEE.toString()))
            .andExpect(jsonPath("$.filiere").value(DEFAULT_FILIERE.toString()))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE.toString()))
            .andExpect(jsonPath("$.autreFiliere").value(DEFAULT_AUTRE_FILIERE))
            .andExpect(jsonPath("$.autreSerie").value(DEFAULT_AUTRE_SERIE))
            .andExpect(jsonPath("$.autreNomEtablissement").value(DEFAULT_AUTRE_NOM_ETABLISSEMENT));
    }

    @Test
    @Transactional
    void getNonExistingEtablissement() throws Exception {
        // Get the etablissement
        restEtablissementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEtablissement() throws Exception {
        // Initialize the database
        insertedEtablissement = etablissementRepository.saveAndFlush(etablissement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the etablissement
        Etablissement updatedEtablissement = etablissementRepository.findById(etablissement.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEtablissement are not directly saved in db
        em.detach(updatedEtablissement);
        updatedEtablissement
            .nomEtablissement(UPDATED_NOM_ETABLISSEMENT)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .region(UPDATED_REGION)
            .departement(UPDATED_DEPARTEMENT)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .typeEtablissement(UPDATED_TYPE_ETABLISSEMENT)
            .statut(UPDATED_STATUT)
            .autreRegion(UPDATED_AUTRE_REGION)
            .autreDepartement(UPDATED_AUTRE_DEPARTEMENT)
            .cfp(UPDATED_CFP)
            .lycee(UPDATED_LYCEE)
            .filiere(UPDATED_FILIERE)
            .serie(UPDATED_SERIE)
            .autreFiliere(UPDATED_AUTRE_FILIERE)
            .autreSerie(UPDATED_AUTRE_SERIE)
            .autreNomEtablissement(UPDATED_AUTRE_NOM_ETABLISSEMENT);

        restEtablissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtablissement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEtablissement))
            )
            .andExpect(status().isOk());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEtablissementToMatchAllProperties(updatedEtablissement);
    }

    @Test
    @Transactional
    void putNonExistingEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etablissement.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtablissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etablissement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(etablissement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etablissement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(etablissement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etablissement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablissementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etablissement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtablissementWithPatch() throws Exception {
        // Initialize the database
        insertedEtablissement = etablissementRepository.saveAndFlush(etablissement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the etablissement using partial update
        Etablissement partialUpdatedEtablissement = new Etablissement();
        partialUpdatedEtablissement.setId(etablissement.getId());

        partialUpdatedEtablissement
            .nomEtablissement(UPDATED_NOM_ETABLISSEMENT)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .typeEtablissement(UPDATED_TYPE_ETABLISSEMENT)
            .autreRegion(UPDATED_AUTRE_REGION)
            .autreDepartement(UPDATED_AUTRE_DEPARTEMENT)
            .lycee(UPDATED_LYCEE)
            .filiere(UPDATED_FILIERE)
            .serie(UPDATED_SERIE)
            .autreSerie(UPDATED_AUTRE_SERIE);

        restEtablissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtablissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEtablissement))
            )
            .andExpect(status().isOk());

        // Validate the Etablissement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEtablissementUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEtablissement, etablissement),
            getPersistedEtablissement(etablissement)
        );
    }

    @Test
    @Transactional
    void fullUpdateEtablissementWithPatch() throws Exception {
        // Initialize the database
        insertedEtablissement = etablissementRepository.saveAndFlush(etablissement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the etablissement using partial update
        Etablissement partialUpdatedEtablissement = new Etablissement();
        partialUpdatedEtablissement.setId(etablissement.getId());

        partialUpdatedEtablissement
            .nomEtablissement(UPDATED_NOM_ETABLISSEMENT)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .region(UPDATED_REGION)
            .departement(UPDATED_DEPARTEMENT)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .typeEtablissement(UPDATED_TYPE_ETABLISSEMENT)
            .statut(UPDATED_STATUT)
            .autreRegion(UPDATED_AUTRE_REGION)
            .autreDepartement(UPDATED_AUTRE_DEPARTEMENT)
            .cfp(UPDATED_CFP)
            .lycee(UPDATED_LYCEE)
            .filiere(UPDATED_FILIERE)
            .serie(UPDATED_SERIE)
            .autreFiliere(UPDATED_AUTRE_FILIERE)
            .autreSerie(UPDATED_AUTRE_SERIE)
            .autreNomEtablissement(UPDATED_AUTRE_NOM_ETABLISSEMENT);

        restEtablissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtablissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEtablissement))
            )
            .andExpect(status().isOk());

        // Validate the Etablissement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEtablissementUpdatableFieldsEquals(partialUpdatedEtablissement, getPersistedEtablissement(partialUpdatedEtablissement));
    }

    @Test
    @Transactional
    void patchNonExistingEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etablissement.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtablissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etablissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(etablissement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etablissement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(etablissement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etablissement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablissementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(etablissement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Etablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtablissement() throws Exception {
        // Initialize the database
        insertedEtablissement = etablissementRepository.saveAndFlush(etablissement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the etablissement
        restEtablissementMockMvc
            .perform(delete(ENTITY_API_URL_ID, etablissement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return etablissementRepository.count();
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

    protected Etablissement getPersistedEtablissement(Etablissement etablissement) {
        return etablissementRepository.findById(etablissement.getId()).orElseThrow();
    }

    protected void assertPersistedEtablissementToMatchAllProperties(Etablissement expectedEtablissement) {
        assertEtablissementAllPropertiesEquals(expectedEtablissement, getPersistedEtablissement(expectedEtablissement));
    }

    protected void assertPersistedEtablissementToMatchUpdatableProperties(Etablissement expectedEtablissement) {
        assertEtablissementAllUpdatablePropertiesEquals(expectedEtablissement, getPersistedEtablissement(expectedEtablissement));
    }
}
