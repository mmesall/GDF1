package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.PriseEnChargeAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PriseEnCharge;
import com.mycompany.myapp.repository.PriseEnChargeRepository;
import com.mycompany.myapp.service.PriseEnChargeService;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
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
 * Integration tests for the {@link PriseEnChargeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PriseEnChargeResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT_PC = 1D;
    private static final Double UPDATED_MONTANT_PC = 2D;

    private static final String ENTITY_API_URL = "/api/prise-en-charges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PriseEnChargeRepository priseEnChargeRepository;

    @Mock
    private PriseEnChargeRepository priseEnChargeRepositoryMock;

    @Mock
    private PriseEnChargeService priseEnChargeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPriseEnChargeMockMvc;

    private PriseEnCharge priseEnCharge;

    private PriseEnCharge insertedPriseEnCharge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriseEnCharge createEntity(EntityManager em) {
        PriseEnCharge priseEnCharge = new PriseEnCharge().libelle(DEFAULT_LIBELLE).montantPC(DEFAULT_MONTANT_PC);
        return priseEnCharge;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriseEnCharge createUpdatedEntity(EntityManager em) {
        PriseEnCharge priseEnCharge = new PriseEnCharge().libelle(UPDATED_LIBELLE).montantPC(UPDATED_MONTANT_PC);
        return priseEnCharge;
    }

    @BeforeEach
    public void initTest() {
        priseEnCharge = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPriseEnCharge != null) {
            priseEnChargeRepository.delete(insertedPriseEnCharge);
            insertedPriseEnCharge = null;
        }
    }

    @Test
    @Transactional
    void createPriseEnCharge() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PriseEnCharge
        var returnedPriseEnCharge = om.readValue(
            restPriseEnChargeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(priseEnCharge)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PriseEnCharge.class
        );

        // Validate the PriseEnCharge in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPriseEnChargeUpdatableFieldsEquals(returnedPriseEnCharge, getPersistedPriseEnCharge(returnedPriseEnCharge));

        insertedPriseEnCharge = returnedPriseEnCharge;
    }

    @Test
    @Transactional
    void createPriseEnChargeWithExistingId() throws Exception {
        // Create the PriseEnCharge with an existing ID
        priseEnCharge.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriseEnChargeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(priseEnCharge)))
            .andExpect(status().isBadRequest());

        // Validate the PriseEnCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPriseEnCharges() throws Exception {
        // Initialize the database
        insertedPriseEnCharge = priseEnChargeRepository.saveAndFlush(priseEnCharge);

        // Get all the priseEnChargeList
        restPriseEnChargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priseEnCharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].montantPC").value(hasItem(DEFAULT_MONTANT_PC.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPriseEnChargesWithEagerRelationshipsIsEnabled() throws Exception {
        when(priseEnChargeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPriseEnChargeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(priseEnChargeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPriseEnChargesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(priseEnChargeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPriseEnChargeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(priseEnChargeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPriseEnCharge() throws Exception {
        // Initialize the database
        insertedPriseEnCharge = priseEnChargeRepository.saveAndFlush(priseEnCharge);

        // Get the priseEnCharge
        restPriseEnChargeMockMvc
            .perform(get(ENTITY_API_URL_ID, priseEnCharge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(priseEnCharge.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.montantPC").value(DEFAULT_MONTANT_PC.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingPriseEnCharge() throws Exception {
        // Get the priseEnCharge
        restPriseEnChargeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPriseEnCharge() throws Exception {
        // Initialize the database
        insertedPriseEnCharge = priseEnChargeRepository.saveAndFlush(priseEnCharge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the priseEnCharge
        PriseEnCharge updatedPriseEnCharge = priseEnChargeRepository.findById(priseEnCharge.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPriseEnCharge are not directly saved in db
        em.detach(updatedPriseEnCharge);
        updatedPriseEnCharge.libelle(UPDATED_LIBELLE).montantPC(UPDATED_MONTANT_PC);

        restPriseEnChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPriseEnCharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPriseEnCharge))
            )
            .andExpect(status().isOk());

        // Validate the PriseEnCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPriseEnChargeToMatchAllProperties(updatedPriseEnCharge);
    }

    @Test
    @Transactional
    void putNonExistingPriseEnCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        priseEnCharge.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriseEnChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, priseEnCharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(priseEnCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the PriseEnCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPriseEnCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        priseEnCharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriseEnChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(priseEnCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the PriseEnCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPriseEnCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        priseEnCharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriseEnChargeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(priseEnCharge)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PriseEnCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePriseEnChargeWithPatch() throws Exception {
        // Initialize the database
        insertedPriseEnCharge = priseEnChargeRepository.saveAndFlush(priseEnCharge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the priseEnCharge using partial update
        PriseEnCharge partialUpdatedPriseEnCharge = new PriseEnCharge();
        partialUpdatedPriseEnCharge.setId(priseEnCharge.getId());

        partialUpdatedPriseEnCharge.libelle(UPDATED_LIBELLE);

        restPriseEnChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPriseEnCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPriseEnCharge))
            )
            .andExpect(status().isOk());

        // Validate the PriseEnCharge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPriseEnChargeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPriseEnCharge, priseEnCharge),
            getPersistedPriseEnCharge(priseEnCharge)
        );
    }

    @Test
    @Transactional
    void fullUpdatePriseEnChargeWithPatch() throws Exception {
        // Initialize the database
        insertedPriseEnCharge = priseEnChargeRepository.saveAndFlush(priseEnCharge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the priseEnCharge using partial update
        PriseEnCharge partialUpdatedPriseEnCharge = new PriseEnCharge();
        partialUpdatedPriseEnCharge.setId(priseEnCharge.getId());

        partialUpdatedPriseEnCharge.libelle(UPDATED_LIBELLE).montantPC(UPDATED_MONTANT_PC);

        restPriseEnChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPriseEnCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPriseEnCharge))
            )
            .andExpect(status().isOk());

        // Validate the PriseEnCharge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPriseEnChargeUpdatableFieldsEquals(partialUpdatedPriseEnCharge, getPersistedPriseEnCharge(partialUpdatedPriseEnCharge));
    }

    @Test
    @Transactional
    void patchNonExistingPriseEnCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        priseEnCharge.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriseEnChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, priseEnCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(priseEnCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the PriseEnCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPriseEnCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        priseEnCharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriseEnChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(priseEnCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the PriseEnCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPriseEnCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        priseEnCharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriseEnChargeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(priseEnCharge)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PriseEnCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePriseEnCharge() throws Exception {
        // Initialize the database
        insertedPriseEnCharge = priseEnChargeRepository.saveAndFlush(priseEnCharge);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the priseEnCharge
        restPriseEnChargeMockMvc
            .perform(delete(ENTITY_API_URL_ID, priseEnCharge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return priseEnChargeRepository.count();
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

    protected PriseEnCharge getPersistedPriseEnCharge(PriseEnCharge priseEnCharge) {
        return priseEnChargeRepository.findById(priseEnCharge.getId()).orElseThrow();
    }

    protected void assertPersistedPriseEnChargeToMatchAllProperties(PriseEnCharge expectedPriseEnCharge) {
        assertPriseEnChargeAllPropertiesEquals(expectedPriseEnCharge, getPersistedPriseEnCharge(expectedPriseEnCharge));
    }

    protected void assertPersistedPriseEnChargeToMatchUpdatableProperties(PriseEnCharge expectedPriseEnCharge) {
        assertPriseEnChargeAllUpdatablePropertiesEquals(expectedPriseEnCharge, getPersistedPriseEnCharge(expectedPriseEnCharge));
    }
}
