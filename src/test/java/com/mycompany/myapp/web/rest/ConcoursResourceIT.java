package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ConcoursAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Concours;
import com.mycompany.myapp.domain.enumeration.NiveauEtude;
import com.mycompany.myapp.domain.enumeration.NomEtablissement;
import com.mycompany.myapp.repository.ConcoursRepository;
import com.mycompany.myapp.service.ConcoursService;
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
 * Integration tests for the {@link ConcoursResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ConcoursResourceIT {

    private static final String DEFAULT_NOM_CONCOURS = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CONCOURS = "BBBBBBBBBB";

    private static final NomEtablissement DEFAULT_NOM_ETABLISSEMENT = NomEtablissement.CEDT_G15;
    private static final NomEtablissement UPDATED_NOM_ETABLISSEMENT = NomEtablissement.CFP_OUAKAM;

    private static final NiveauEtude DEFAULT_NIVEAU_ETUDE = NiveauEtude.CINQUIEME;
    private static final NiveauEtude UPDATED_NIVEAU_ETUDE = NiveauEtude.QUATRIEME;

    private static final LocalDate DEFAULT_DATE_OUVERTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OUVERTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CLOTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CLOTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CONCOURS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CONCOURS = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_AFFICHE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AFFICHE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AFFICHE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AFFICHE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/concours";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ConcoursRepository concoursRepository;

    @Mock
    private ConcoursRepository concoursRepositoryMock;

    @Mock
    private ConcoursService concoursServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConcoursMockMvc;

    private Concours concours;

    private Concours insertedConcours;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concours createEntity(EntityManager em) {
        Concours concours = new Concours()
            .nomConcours(DEFAULT_NOM_CONCOURS)
            .nomEtablissement(DEFAULT_NOM_ETABLISSEMENT)
            .niveauEtude(DEFAULT_NIVEAU_ETUDE)
            .dateOuverture(DEFAULT_DATE_OUVERTURE)
            .dateCloture(DEFAULT_DATE_CLOTURE)
            .dateConcours(DEFAULT_DATE_CONCOURS)
            .affiche(DEFAULT_AFFICHE)
            .afficheContentType(DEFAULT_AFFICHE_CONTENT_TYPE);
        return concours;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concours createUpdatedEntity(EntityManager em) {
        Concours concours = new Concours()
            .nomConcours(UPDATED_NOM_CONCOURS)
            .nomEtablissement(UPDATED_NOM_ETABLISSEMENT)
            .niveauEtude(UPDATED_NIVEAU_ETUDE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .dateConcours(UPDATED_DATE_CONCOURS)
            .affiche(UPDATED_AFFICHE)
            .afficheContentType(UPDATED_AFFICHE_CONTENT_TYPE);
        return concours;
    }

    @BeforeEach
    public void initTest() {
        concours = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedConcours != null) {
            concoursRepository.delete(insertedConcours);
            insertedConcours = null;
        }
    }

    @Test
    @Transactional
    void createConcours() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Concours
        var returnedConcours = om.readValue(
            restConcoursMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(concours)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Concours.class
        );

        // Validate the Concours in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertConcoursUpdatableFieldsEquals(returnedConcours, getPersistedConcours(returnedConcours));

        insertedConcours = returnedConcours;
    }

    @Test
    @Transactional
    void createConcoursWithExistingId() throws Exception {
        // Create the Concours with an existing ID
        concours.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcoursMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(concours)))
            .andExpect(status().isBadRequest());

        // Validate the Concours in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConcours() throws Exception {
        // Initialize the database
        insertedConcours = concoursRepository.saveAndFlush(concours);

        // Get all the concoursList
        restConcoursMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concours.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomConcours").value(hasItem(DEFAULT_NOM_CONCOURS)))
            .andExpect(jsonPath("$.[*].nomEtablissement").value(hasItem(DEFAULT_NOM_ETABLISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].niveauEtude").value(hasItem(DEFAULT_NIVEAU_ETUDE.toString())))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].dateCloture").value(hasItem(DEFAULT_DATE_CLOTURE.toString())))
            .andExpect(jsonPath("$.[*].dateConcours").value(hasItem(DEFAULT_DATE_CONCOURS.toString())))
            .andExpect(jsonPath("$.[*].afficheContentType").value(hasItem(DEFAULT_AFFICHE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].affiche").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_AFFICHE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConcoursWithEagerRelationshipsIsEnabled() throws Exception {
        when(concoursServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConcoursMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(concoursServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConcoursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(concoursServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConcoursMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(concoursRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getConcours() throws Exception {
        // Initialize the database
        insertedConcours = concoursRepository.saveAndFlush(concours);

        // Get the concours
        restConcoursMockMvc
            .perform(get(ENTITY_API_URL_ID, concours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concours.getId().intValue()))
            .andExpect(jsonPath("$.nomConcours").value(DEFAULT_NOM_CONCOURS))
            .andExpect(jsonPath("$.nomEtablissement").value(DEFAULT_NOM_ETABLISSEMENT.toString()))
            .andExpect(jsonPath("$.niveauEtude").value(DEFAULT_NIVEAU_ETUDE.toString()))
            .andExpect(jsonPath("$.dateOuverture").value(DEFAULT_DATE_OUVERTURE.toString()))
            .andExpect(jsonPath("$.dateCloture").value(DEFAULT_DATE_CLOTURE.toString()))
            .andExpect(jsonPath("$.dateConcours").value(DEFAULT_DATE_CONCOURS.toString()))
            .andExpect(jsonPath("$.afficheContentType").value(DEFAULT_AFFICHE_CONTENT_TYPE))
            .andExpect(jsonPath("$.affiche").value(Base64.getEncoder().encodeToString(DEFAULT_AFFICHE)));
    }

    @Test
    @Transactional
    void getNonExistingConcours() throws Exception {
        // Get the concours
        restConcoursMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConcours() throws Exception {
        // Initialize the database
        insertedConcours = concoursRepository.saveAndFlush(concours);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the concours
        Concours updatedConcours = concoursRepository.findById(concours.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedConcours are not directly saved in db
        em.detach(updatedConcours);
        updatedConcours
            .nomConcours(UPDATED_NOM_CONCOURS)
            .nomEtablissement(UPDATED_NOM_ETABLISSEMENT)
            .niveauEtude(UPDATED_NIVEAU_ETUDE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .dateConcours(UPDATED_DATE_CONCOURS)
            .affiche(UPDATED_AFFICHE)
            .afficheContentType(UPDATED_AFFICHE_CONTENT_TYPE);

        restConcoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConcours.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedConcours))
            )
            .andExpect(status().isOk());

        // Validate the Concours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedConcoursToMatchAllProperties(updatedConcours);
    }

    @Test
    @Transactional
    void putNonExistingConcours() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        concours.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, concours.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(concours))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConcours() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        concours.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(concours))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConcours() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        concours.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcoursMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(concours)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConcoursWithPatch() throws Exception {
        // Initialize the database
        insertedConcours = concoursRepository.saveAndFlush(concours);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the concours using partial update
        Concours partialUpdatedConcours = new Concours();
        partialUpdatedConcours.setId(concours.getId());

        partialUpdatedConcours
            .nomEtablissement(UPDATED_NOM_ETABLISSEMENT)
            .niveauEtude(UPDATED_NIVEAU_ETUDE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateConcours(UPDATED_DATE_CONCOURS);

        restConcoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcours.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConcours))
            )
            .andExpect(status().isOk());

        // Validate the Concours in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConcoursUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedConcours, concours), getPersistedConcours(concours));
    }

    @Test
    @Transactional
    void fullUpdateConcoursWithPatch() throws Exception {
        // Initialize the database
        insertedConcours = concoursRepository.saveAndFlush(concours);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the concours using partial update
        Concours partialUpdatedConcours = new Concours();
        partialUpdatedConcours.setId(concours.getId());

        partialUpdatedConcours
            .nomConcours(UPDATED_NOM_CONCOURS)
            .nomEtablissement(UPDATED_NOM_ETABLISSEMENT)
            .niveauEtude(UPDATED_NIVEAU_ETUDE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .dateConcours(UPDATED_DATE_CONCOURS)
            .affiche(UPDATED_AFFICHE)
            .afficheContentType(UPDATED_AFFICHE_CONTENT_TYPE);

        restConcoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcours.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConcours))
            )
            .andExpect(status().isOk());

        // Validate the Concours in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConcoursUpdatableFieldsEquals(partialUpdatedConcours, getPersistedConcours(partialUpdatedConcours));
    }

    @Test
    @Transactional
    void patchNonExistingConcours() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        concours.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, concours.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(concours))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConcours() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        concours.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(concours))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConcours() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        concours.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcoursMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(concours)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConcours() throws Exception {
        // Initialize the database
        insertedConcours = concoursRepository.saveAndFlush(concours);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the concours
        restConcoursMockMvc
            .perform(delete(ENTITY_API_URL_ID, concours.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return concoursRepository.count();
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

    protected Concours getPersistedConcours(Concours concours) {
        return concoursRepository.findById(concours.getId()).orElseThrow();
    }

    protected void assertPersistedConcoursToMatchAllProperties(Concours expectedConcours) {
        assertConcoursAllPropertiesEquals(expectedConcours, getPersistedConcours(expectedConcours));
    }

    protected void assertPersistedConcoursToMatchUpdatableProperties(Concours expectedConcours) {
        assertConcoursAllUpdatablePropertiesEquals(expectedConcours, getPersistedConcours(expectedConcours));
    }
}
