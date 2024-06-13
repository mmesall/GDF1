package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CandidatureEAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CandidatureE;
import com.mycompany.myapp.domain.enumeration.NomFiliere;
import com.mycompany.myapp.domain.enumeration.Resultat;
import com.mycompany.myapp.repository.CandidatureERepository;
import com.mycompany.myapp.service.CandidatureEService;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link CandidatureEResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CandidatureEResourceIT {

    private static final NomFiliere DEFAULT_OFFRE_FORMATION = NomFiliere.AGRI_ELEVAGE;
    private static final NomFiliere UPDATED_OFFRE_FORMATION = NomFiliere.AGRICULTURE;

    private static final LocalDate DEFAULT_DATE_DEBUT_OFFRE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT_OFFRE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN_OFFRE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN_OFFRE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DEPOT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEPOT = LocalDate.now(ZoneId.systemDefault());

    private static final Resultat DEFAULT_RESULTAT = Resultat.SOUMIS;
    private static final Resultat UPDATED_RESULTAT = Resultat.VALIDE;

    private static final String ENTITY_API_URL = "/api/candidature-es";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CandidatureERepository candidatureERepository;

    @Mock
    private CandidatureERepository candidatureERepositoryMock;

    @Mock
    private CandidatureEService candidatureEServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidatureEMockMvc;

    private CandidatureE candidatureE;

    private CandidatureE insertedCandidatureE;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidatureE createEntity(EntityManager em) {
        CandidatureE candidatureE = new CandidatureE()
            .offreFormation(DEFAULT_OFFRE_FORMATION)
            .dateDebutOffre(DEFAULT_DATE_DEBUT_OFFRE)
            .dateFinOffre(DEFAULT_DATE_FIN_OFFRE)
            .dateDepot(DEFAULT_DATE_DEPOT)
            .resultat(DEFAULT_RESULTAT);
        return candidatureE;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidatureE createUpdatedEntity(EntityManager em) {
        CandidatureE candidatureE = new CandidatureE()
            .offreFormation(UPDATED_OFFRE_FORMATION)
            .dateDebutOffre(UPDATED_DATE_DEBUT_OFFRE)
            .dateFinOffre(UPDATED_DATE_FIN_OFFRE)
            .dateDepot(UPDATED_DATE_DEPOT)
            .resultat(UPDATED_RESULTAT);
        return candidatureE;
    }

    @BeforeEach
    public void initTest() {
        candidatureE = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCandidatureE != null) {
            candidatureERepository.delete(insertedCandidatureE);
            insertedCandidatureE = null;
        }
    }

    @Test
    @Transactional
    void createCandidatureE() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CandidatureE
        var returnedCandidatureE = om.readValue(
            restCandidatureEMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidatureE)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CandidatureE.class
        );

        // Validate the CandidatureE in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCandidatureEUpdatableFieldsEquals(returnedCandidatureE, getPersistedCandidatureE(returnedCandidatureE));

        insertedCandidatureE = returnedCandidatureE;
    }

    @Test
    @Transactional
    void createCandidatureEWithExistingId() throws Exception {
        // Create the CandidatureE with an existing ID
        candidatureE.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidatureEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidatureE)))
            .andExpect(status().isBadRequest());

        // Validate the CandidatureE in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCandidatureES() throws Exception {
        // Initialize the database
        insertedCandidatureE = candidatureERepository.saveAndFlush(candidatureE);

        // Get all the candidatureEList
        restCandidatureEMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidatureE.getId().intValue())))
            .andExpect(jsonPath("$.[*].offreFormation").value(hasItem(DEFAULT_OFFRE_FORMATION.toString())))
            .andExpect(jsonPath("$.[*].dateDebutOffre").value(hasItem(DEFAULT_DATE_DEBUT_OFFRE.toString())))
            .andExpect(jsonPath("$.[*].dateFinOffre").value(hasItem(DEFAULT_DATE_FIN_OFFRE.toString())))
            .andExpect(jsonPath("$.[*].dateDepot").value(hasItem(DEFAULT_DATE_DEPOT.toString())))
            .andExpect(jsonPath("$.[*].resultat").value(hasItem(DEFAULT_RESULTAT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCandidatureESWithEagerRelationshipsIsEnabled() throws Exception {
        when(candidatureEServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCandidatureEMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(candidatureEServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCandidatureESWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(candidatureEServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCandidatureEMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(candidatureERepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCandidatureE() throws Exception {
        // Initialize the database
        insertedCandidatureE = candidatureERepository.saveAndFlush(candidatureE);

        // Get the candidatureE
        restCandidatureEMockMvc
            .perform(get(ENTITY_API_URL_ID, candidatureE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidatureE.getId().intValue()))
            .andExpect(jsonPath("$.offreFormation").value(DEFAULT_OFFRE_FORMATION.toString()))
            .andExpect(jsonPath("$.dateDebutOffre").value(DEFAULT_DATE_DEBUT_OFFRE.toString()))
            .andExpect(jsonPath("$.dateFinOffre").value(DEFAULT_DATE_FIN_OFFRE.toString()))
            .andExpect(jsonPath("$.dateDepot").value(DEFAULT_DATE_DEPOT.toString()))
            .andExpect(jsonPath("$.resultat").value(DEFAULT_RESULTAT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCandidatureE() throws Exception {
        // Get the candidatureE
        restCandidatureEMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCandidatureE() throws Exception {
        // Initialize the database
        insertedCandidatureE = candidatureERepository.saveAndFlush(candidatureE);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the candidatureE
        CandidatureE updatedCandidatureE = candidatureERepository.findById(candidatureE.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCandidatureE are not directly saved in db
        em.detach(updatedCandidatureE);
        updatedCandidatureE
            .offreFormation(UPDATED_OFFRE_FORMATION)
            .dateDebutOffre(UPDATED_DATE_DEBUT_OFFRE)
            .dateFinOffre(UPDATED_DATE_FIN_OFFRE)
            .dateDepot(UPDATED_DATE_DEPOT)
            .resultat(UPDATED_RESULTAT);

        restCandidatureEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCandidatureE.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCandidatureE))
            )
            .andExpect(status().isOk());

        // Validate the CandidatureE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCandidatureEToMatchAllProperties(updatedCandidatureE);
    }

    @Test
    @Transactional
    void putNonExistingCandidatureE() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidatureE.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidatureEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidatureE.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(candidatureE))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidatureE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCandidatureE() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidatureE.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidatureEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(candidatureE))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidatureE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCandidatureE() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidatureE.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidatureEMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidatureE)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CandidatureE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCandidatureEWithPatch() throws Exception {
        // Initialize the database
        insertedCandidatureE = candidatureERepository.saveAndFlush(candidatureE);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the candidatureE using partial update
        CandidatureE partialUpdatedCandidatureE = new CandidatureE();
        partialUpdatedCandidatureE.setId(candidatureE.getId());

        partialUpdatedCandidatureE.resultat(UPDATED_RESULTAT);

        restCandidatureEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidatureE.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCandidatureE))
            )
            .andExpect(status().isOk());

        // Validate the CandidatureE in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCandidatureEUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCandidatureE, candidatureE),
            getPersistedCandidatureE(candidatureE)
        );
    }

    @Test
    @Transactional
    void fullUpdateCandidatureEWithPatch() throws Exception {
        // Initialize the database
        insertedCandidatureE = candidatureERepository.saveAndFlush(candidatureE);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the candidatureE using partial update
        CandidatureE partialUpdatedCandidatureE = new CandidatureE();
        partialUpdatedCandidatureE.setId(candidatureE.getId());

        partialUpdatedCandidatureE
            .offreFormation(UPDATED_OFFRE_FORMATION)
            .dateDebutOffre(UPDATED_DATE_DEBUT_OFFRE)
            .dateFinOffre(UPDATED_DATE_FIN_OFFRE)
            .dateDepot(UPDATED_DATE_DEPOT)
            .resultat(UPDATED_RESULTAT);

        restCandidatureEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidatureE.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCandidatureE))
            )
            .andExpect(status().isOk());

        // Validate the CandidatureE in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCandidatureEUpdatableFieldsEquals(partialUpdatedCandidatureE, getPersistedCandidatureE(partialUpdatedCandidatureE));
    }

    @Test
    @Transactional
    void patchNonExistingCandidatureE() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidatureE.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidatureEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, candidatureE.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(candidatureE))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidatureE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCandidatureE() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidatureE.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidatureEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(candidatureE))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidatureE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCandidatureE() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidatureE.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidatureEMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(candidatureE)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CandidatureE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCandidatureE() throws Exception {
        // Initialize the database
        insertedCandidatureE = candidatureERepository.saveAndFlush(candidatureE);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the candidatureE
        restCandidatureEMockMvc
            .perform(delete(ENTITY_API_URL_ID, candidatureE.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return candidatureERepository.count();
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

    protected CandidatureE getPersistedCandidatureE(CandidatureE candidatureE) {
        return candidatureERepository.findById(candidatureE.getId()).orElseThrow();
    }

    protected void assertPersistedCandidatureEToMatchAllProperties(CandidatureE expectedCandidatureE) {
        assertCandidatureEAllPropertiesEquals(expectedCandidatureE, getPersistedCandidatureE(expectedCandidatureE));
    }

    protected void assertPersistedCandidatureEToMatchUpdatableProperties(CandidatureE expectedCandidatureE) {
        assertCandidatureEAllUpdatablePropertiesEquals(expectedCandidatureE, getPersistedCandidatureE(expectedCandidatureE));
    }
}
