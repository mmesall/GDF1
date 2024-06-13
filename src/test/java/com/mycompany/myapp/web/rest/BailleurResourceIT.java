package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.BailleurAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Bailleur;
import com.mycompany.myapp.repository.BailleurRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link BailleurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BailleurResourceIT {

    private static final String DEFAULT_NOM_BAILLEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_BAILLEUR = "BBBBBBBBBB";

    private static final Double DEFAULT_BUDGET_PREVU = 1D;
    private static final Double UPDATED_BUDGET_PREVU = 2D;

    private static final Double DEFAULT_BUDGET_DEPENSE = 1D;
    private static final Double UPDATED_BUDGET_DEPENSE = 2D;

    private static final Double DEFAULT_BUDGET_RESTANT = 1D;
    private static final Double UPDATED_BUDGET_RESTANT = 2D;

    private static final Long DEFAULT_NBRE_PC = 1L;
    private static final Long UPDATED_NBRE_PC = 2L;

    private static final String ENTITY_API_URL = "/api/bailleurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BailleurRepository bailleurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBailleurMockMvc;

    private Bailleur bailleur;

    private Bailleur insertedBailleur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bailleur createEntity(EntityManager em) {
        Bailleur bailleur = new Bailleur()
            .nomBailleur(DEFAULT_NOM_BAILLEUR)
            .budgetPrevu(DEFAULT_BUDGET_PREVU)
            .budgetDepense(DEFAULT_BUDGET_DEPENSE)
            .budgetRestant(DEFAULT_BUDGET_RESTANT)
            .nbrePC(DEFAULT_NBRE_PC);
        return bailleur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bailleur createUpdatedEntity(EntityManager em) {
        Bailleur bailleur = new Bailleur()
            .nomBailleur(UPDATED_NOM_BAILLEUR)
            .budgetPrevu(UPDATED_BUDGET_PREVU)
            .budgetDepense(UPDATED_BUDGET_DEPENSE)
            .budgetRestant(UPDATED_BUDGET_RESTANT)
            .nbrePC(UPDATED_NBRE_PC);
        return bailleur;
    }

    @BeforeEach
    public void initTest() {
        bailleur = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedBailleur != null) {
            bailleurRepository.delete(insertedBailleur);
            insertedBailleur = null;
        }
    }

    @Test
    @Transactional
    void createBailleur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bailleur
        var returnedBailleur = om.readValue(
            restBailleurMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bailleur)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bailleur.class
        );

        // Validate the Bailleur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBailleurUpdatableFieldsEquals(returnedBailleur, getPersistedBailleur(returnedBailleur));

        insertedBailleur = returnedBailleur;
    }

    @Test
    @Transactional
    void createBailleurWithExistingId() throws Exception {
        // Create the Bailleur with an existing ID
        bailleur.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBailleurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bailleur)))
            .andExpect(status().isBadRequest());

        // Validate the Bailleur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomBailleurIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bailleur.setNomBailleur(null);

        // Create the Bailleur, which fails.

        restBailleurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bailleur)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBailleurs() throws Exception {
        // Initialize the database
        insertedBailleur = bailleurRepository.saveAndFlush(bailleur);

        // Get all the bailleurList
        restBailleurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bailleur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomBailleur").value(hasItem(DEFAULT_NOM_BAILLEUR)))
            .andExpect(jsonPath("$.[*].budgetPrevu").value(hasItem(DEFAULT_BUDGET_PREVU.doubleValue())))
            .andExpect(jsonPath("$.[*].budgetDepense").value(hasItem(DEFAULT_BUDGET_DEPENSE.doubleValue())))
            .andExpect(jsonPath("$.[*].budgetRestant").value(hasItem(DEFAULT_BUDGET_RESTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].nbrePC").value(hasItem(DEFAULT_NBRE_PC.intValue())));
    }

    @Test
    @Transactional
    void getBailleur() throws Exception {
        // Initialize the database
        insertedBailleur = bailleurRepository.saveAndFlush(bailleur);

        // Get the bailleur
        restBailleurMockMvc
            .perform(get(ENTITY_API_URL_ID, bailleur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bailleur.getId().intValue()))
            .andExpect(jsonPath("$.nomBailleur").value(DEFAULT_NOM_BAILLEUR))
            .andExpect(jsonPath("$.budgetPrevu").value(DEFAULT_BUDGET_PREVU.doubleValue()))
            .andExpect(jsonPath("$.budgetDepense").value(DEFAULT_BUDGET_DEPENSE.doubleValue()))
            .andExpect(jsonPath("$.budgetRestant").value(DEFAULT_BUDGET_RESTANT.doubleValue()))
            .andExpect(jsonPath("$.nbrePC").value(DEFAULT_NBRE_PC.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBailleur() throws Exception {
        // Get the bailleur
        restBailleurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBailleur() throws Exception {
        // Initialize the database
        insertedBailleur = bailleurRepository.saveAndFlush(bailleur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bailleur
        Bailleur updatedBailleur = bailleurRepository.findById(bailleur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBailleur are not directly saved in db
        em.detach(updatedBailleur);
        updatedBailleur
            .nomBailleur(UPDATED_NOM_BAILLEUR)
            .budgetPrevu(UPDATED_BUDGET_PREVU)
            .budgetDepense(UPDATED_BUDGET_DEPENSE)
            .budgetRestant(UPDATED_BUDGET_RESTANT)
            .nbrePC(UPDATED_NBRE_PC);

        restBailleurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBailleur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBailleur))
            )
            .andExpect(status().isOk());

        // Validate the Bailleur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBailleurToMatchAllProperties(updatedBailleur);
    }

    @Test
    @Transactional
    void putNonExistingBailleur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bailleur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBailleurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bailleur.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bailleur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bailleur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBailleur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bailleur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBailleurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bailleur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bailleur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBailleur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bailleur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBailleurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bailleur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bailleur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBailleurWithPatch() throws Exception {
        // Initialize the database
        insertedBailleur = bailleurRepository.saveAndFlush(bailleur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bailleur using partial update
        Bailleur partialUpdatedBailleur = new Bailleur();
        partialUpdatedBailleur.setId(bailleur.getId());

        partialUpdatedBailleur
            .budgetPrevu(UPDATED_BUDGET_PREVU)
            .budgetDepense(UPDATED_BUDGET_DEPENSE)
            .budgetRestant(UPDATED_BUDGET_RESTANT);

        restBailleurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBailleur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBailleur))
            )
            .andExpect(status().isOk());

        // Validate the Bailleur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBailleurUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBailleur, bailleur), getPersistedBailleur(bailleur));
    }

    @Test
    @Transactional
    void fullUpdateBailleurWithPatch() throws Exception {
        // Initialize the database
        insertedBailleur = bailleurRepository.saveAndFlush(bailleur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bailleur using partial update
        Bailleur partialUpdatedBailleur = new Bailleur();
        partialUpdatedBailleur.setId(bailleur.getId());

        partialUpdatedBailleur
            .nomBailleur(UPDATED_NOM_BAILLEUR)
            .budgetPrevu(UPDATED_BUDGET_PREVU)
            .budgetDepense(UPDATED_BUDGET_DEPENSE)
            .budgetRestant(UPDATED_BUDGET_RESTANT)
            .nbrePC(UPDATED_NBRE_PC);

        restBailleurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBailleur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBailleur))
            )
            .andExpect(status().isOk());

        // Validate the Bailleur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBailleurUpdatableFieldsEquals(partialUpdatedBailleur, getPersistedBailleur(partialUpdatedBailleur));
    }

    @Test
    @Transactional
    void patchNonExistingBailleur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bailleur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBailleurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bailleur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bailleur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bailleur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBailleur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bailleur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBailleurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bailleur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bailleur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBailleur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bailleur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBailleurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bailleur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bailleur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBailleur() throws Exception {
        // Initialize the database
        insertedBailleur = bailleurRepository.saveAndFlush(bailleur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bailleur
        restBailleurMockMvc
            .perform(delete(ENTITY_API_URL_ID, bailleur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bailleurRepository.count();
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

    protected Bailleur getPersistedBailleur(Bailleur bailleur) {
        return bailleurRepository.findById(bailleur.getId()).orElseThrow();
    }

    protected void assertPersistedBailleurToMatchAllProperties(Bailleur expectedBailleur) {
        assertBailleurAllPropertiesEquals(expectedBailleur, getPersistedBailleur(expectedBailleur));
    }

    protected void assertPersistedBailleurToMatchUpdatableProperties(Bailleur expectedBailleur) {
        assertBailleurAllUpdatablePropertiesEquals(expectedBailleur, getPersistedBailleur(expectedBailleur));
    }
}
