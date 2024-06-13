package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CandidaturePAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CandidatureP;
import com.mycompany.myapp.domain.enumeration.NomFiliere;
import com.mycompany.myapp.domain.enumeration.Resultat;
import com.mycompany.myapp.repository.CandidaturePRepository;
import com.mycompany.myapp.service.CandidaturePService;
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
 * Integration tests for the {@link CandidaturePResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CandidaturePResourceIT {

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

    private static final String ENTITY_API_URL = "/api/candidature-ps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CandidaturePRepository candidaturePRepository;

    @Mock
    private CandidaturePRepository candidaturePRepositoryMock;

    @Mock
    private CandidaturePService candidaturePServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidaturePMockMvc;

    private CandidatureP candidatureP;

    private CandidatureP insertedCandidatureP;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidatureP createEntity(EntityManager em) {
        CandidatureP candidatureP = new CandidatureP()
            .offreFormation(DEFAULT_OFFRE_FORMATION)
            .dateDebutOffre(DEFAULT_DATE_DEBUT_OFFRE)
            .dateFinOffre(DEFAULT_DATE_FIN_OFFRE)
            .dateDepot(DEFAULT_DATE_DEPOT)
            .resultat(DEFAULT_RESULTAT);
        return candidatureP;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidatureP createUpdatedEntity(EntityManager em) {
        CandidatureP candidatureP = new CandidatureP()
            .offreFormation(UPDATED_OFFRE_FORMATION)
            .dateDebutOffre(UPDATED_DATE_DEBUT_OFFRE)
            .dateFinOffre(UPDATED_DATE_FIN_OFFRE)
            .dateDepot(UPDATED_DATE_DEPOT)
            .resultat(UPDATED_RESULTAT);
        return candidatureP;
    }

    @BeforeEach
    public void initTest() {
        candidatureP = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCandidatureP != null) {
            candidaturePRepository.delete(insertedCandidatureP);
            insertedCandidatureP = null;
        }
    }

    @Test
    @Transactional
    void createCandidatureP() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CandidatureP
        var returnedCandidatureP = om.readValue(
            restCandidaturePMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidatureP)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CandidatureP.class
        );

        // Validate the CandidatureP in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCandidaturePUpdatableFieldsEquals(returnedCandidatureP, getPersistedCandidatureP(returnedCandidatureP));

        insertedCandidatureP = returnedCandidatureP;
    }

    @Test
    @Transactional
    void createCandidaturePWithExistingId() throws Exception {
        // Create the CandidatureP with an existing ID
        candidatureP.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidaturePMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidatureP)))
            .andExpect(status().isBadRequest());

        // Validate the CandidatureP in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCandidaturePS() throws Exception {
        // Initialize the database
        insertedCandidatureP = candidaturePRepository.saveAndFlush(candidatureP);

        // Get all the candidaturePList
        restCandidaturePMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidatureP.getId().intValue())))
            .andExpect(jsonPath("$.[*].offreFormation").value(hasItem(DEFAULT_OFFRE_FORMATION.toString())))
            .andExpect(jsonPath("$.[*].dateDebutOffre").value(hasItem(DEFAULT_DATE_DEBUT_OFFRE.toString())))
            .andExpect(jsonPath("$.[*].dateFinOffre").value(hasItem(DEFAULT_DATE_FIN_OFFRE.toString())))
            .andExpect(jsonPath("$.[*].dateDepot").value(hasItem(DEFAULT_DATE_DEPOT.toString())))
            .andExpect(jsonPath("$.[*].resultat").value(hasItem(DEFAULT_RESULTAT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCandidaturePSWithEagerRelationshipsIsEnabled() throws Exception {
        when(candidaturePServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCandidaturePMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(candidaturePServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCandidaturePSWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(candidaturePServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCandidaturePMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(candidaturePRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCandidatureP() throws Exception {
        // Initialize the database
        insertedCandidatureP = candidaturePRepository.saveAndFlush(candidatureP);

        // Get the candidatureP
        restCandidaturePMockMvc
            .perform(get(ENTITY_API_URL_ID, candidatureP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidatureP.getId().intValue()))
            .andExpect(jsonPath("$.offreFormation").value(DEFAULT_OFFRE_FORMATION.toString()))
            .andExpect(jsonPath("$.dateDebutOffre").value(DEFAULT_DATE_DEBUT_OFFRE.toString()))
            .andExpect(jsonPath("$.dateFinOffre").value(DEFAULT_DATE_FIN_OFFRE.toString()))
            .andExpect(jsonPath("$.dateDepot").value(DEFAULT_DATE_DEPOT.toString()))
            .andExpect(jsonPath("$.resultat").value(DEFAULT_RESULTAT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCandidatureP() throws Exception {
        // Get the candidatureP
        restCandidaturePMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCandidatureP() throws Exception {
        // Initialize the database
        insertedCandidatureP = candidaturePRepository.saveAndFlush(candidatureP);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the candidatureP
        CandidatureP updatedCandidatureP = candidaturePRepository.findById(candidatureP.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCandidatureP are not directly saved in db
        em.detach(updatedCandidatureP);
        updatedCandidatureP
            .offreFormation(UPDATED_OFFRE_FORMATION)
            .dateDebutOffre(UPDATED_DATE_DEBUT_OFFRE)
            .dateFinOffre(UPDATED_DATE_FIN_OFFRE)
            .dateDepot(UPDATED_DATE_DEPOT)
            .resultat(UPDATED_RESULTAT);

        restCandidaturePMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCandidatureP.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCandidatureP))
            )
            .andExpect(status().isOk());

        // Validate the CandidatureP in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCandidaturePToMatchAllProperties(updatedCandidatureP);
    }

    @Test
    @Transactional
    void putNonExistingCandidatureP() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidatureP.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidaturePMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidatureP.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(candidatureP))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidatureP in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCandidatureP() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidatureP.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidaturePMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(candidatureP))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidatureP in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCandidatureP() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidatureP.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidaturePMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(candidatureP)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CandidatureP in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCandidaturePWithPatch() throws Exception {
        // Initialize the database
        insertedCandidatureP = candidaturePRepository.saveAndFlush(candidatureP);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the candidatureP using partial update
        CandidatureP partialUpdatedCandidatureP = new CandidatureP();
        partialUpdatedCandidatureP.setId(candidatureP.getId());

        partialUpdatedCandidatureP
            .offreFormation(UPDATED_OFFRE_FORMATION)
            .dateDebutOffre(UPDATED_DATE_DEBUT_OFFRE)
            .dateFinOffre(UPDATED_DATE_FIN_OFFRE)
            .resultat(UPDATED_RESULTAT);

        restCandidaturePMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidatureP.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCandidatureP))
            )
            .andExpect(status().isOk());

        // Validate the CandidatureP in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCandidaturePUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCandidatureP, candidatureP),
            getPersistedCandidatureP(candidatureP)
        );
    }

    @Test
    @Transactional
    void fullUpdateCandidaturePWithPatch() throws Exception {
        // Initialize the database
        insertedCandidatureP = candidaturePRepository.saveAndFlush(candidatureP);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the candidatureP using partial update
        CandidatureP partialUpdatedCandidatureP = new CandidatureP();
        partialUpdatedCandidatureP.setId(candidatureP.getId());

        partialUpdatedCandidatureP
            .offreFormation(UPDATED_OFFRE_FORMATION)
            .dateDebutOffre(UPDATED_DATE_DEBUT_OFFRE)
            .dateFinOffre(UPDATED_DATE_FIN_OFFRE)
            .dateDepot(UPDATED_DATE_DEPOT)
            .resultat(UPDATED_RESULTAT);

        restCandidaturePMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidatureP.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCandidatureP))
            )
            .andExpect(status().isOk());

        // Validate the CandidatureP in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCandidaturePUpdatableFieldsEquals(partialUpdatedCandidatureP, getPersistedCandidatureP(partialUpdatedCandidatureP));
    }

    @Test
    @Transactional
    void patchNonExistingCandidatureP() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidatureP.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidaturePMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, candidatureP.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(candidatureP))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidatureP in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCandidatureP() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidatureP.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidaturePMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(candidatureP))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidatureP in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCandidatureP() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        candidatureP.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidaturePMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(candidatureP)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CandidatureP in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCandidatureP() throws Exception {
        // Initialize the database
        insertedCandidatureP = candidaturePRepository.saveAndFlush(candidatureP);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the candidatureP
        restCandidaturePMockMvc
            .perform(delete(ENTITY_API_URL_ID, candidatureP.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return candidaturePRepository.count();
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

    protected CandidatureP getPersistedCandidatureP(CandidatureP candidatureP) {
        return candidaturePRepository.findById(candidatureP.getId()).orElseThrow();
    }

    protected void assertPersistedCandidaturePToMatchAllProperties(CandidatureP expectedCandidatureP) {
        assertCandidaturePAllPropertiesEquals(expectedCandidatureP, getPersistedCandidatureP(expectedCandidatureP));
    }

    protected void assertPersistedCandidaturePToMatchUpdatableProperties(CandidatureP expectedCandidatureP) {
        assertCandidaturePAllUpdatablePropertiesEquals(expectedCandidatureP, getPersistedCandidatureP(expectedCandidatureP));
    }
}
