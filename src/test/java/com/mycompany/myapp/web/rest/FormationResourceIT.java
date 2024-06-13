package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.FormationAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Formation;
import com.mycompany.myapp.domain.enumeration.Admission;
import com.mycompany.myapp.domain.enumeration.DiplomeRequis;
import com.mycompany.myapp.domain.enumeration.TypeFormation;
import com.mycompany.myapp.repository.FormationRepository;
import com.mycompany.myapp.service.FormationService;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
 * Integration tests for the {@link FormationResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FormationResourceIT {

    private static final String DEFAULT_NOM_FORMATION = "AAAAAAAAAA";
    private static final String UPDATED_NOM_FORMATION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_FORMATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_FORMATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_FORMATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_FORMATION_CONTENT_TYPE = "image/png";

    private static final TypeFormation DEFAULT_TYPE_FORMATION = TypeFormation.INITIALE;
    private static final TypeFormation UPDATED_TYPE_FORMATION = TypeFormation.CONTINUE;

    private static final String DEFAULT_DUREE = "AAAAAAAAAA";
    private static final String UPDATED_DUREE = "BBBBBBBBBB";

    private static final Admission DEFAULT_ADMISSION = Admission.CONCOURS;
    private static final Admission UPDATED_ADMISSION = Admission.PC;

    private static final DiplomeRequis DEFAULT_DIPLOME_REQUIS = DiplomeRequis.ATTESTATION;
    private static final DiplomeRequis UPDATED_DIPLOME_REQUIS = DiplomeRequis.CAP;

    private static final byte[] DEFAULT_FICHE_FORMATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHE_FORMATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHE_FORMATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHE_FORMATION_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/formations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FormationRepository formationRepository;

    @Mock
    private FormationRepository formationRepositoryMock;

    @Mock
    private FormationService formationServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormationMockMvc;

    private Formation formation;

    private Formation insertedFormation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formation createEntity(EntityManager em) {
        Formation formation = new Formation()
            .nomFormation(DEFAULT_NOM_FORMATION)
            .imageFormation(DEFAULT_IMAGE_FORMATION)
            .imageFormationContentType(DEFAULT_IMAGE_FORMATION_CONTENT_TYPE)
            .typeFormation(DEFAULT_TYPE_FORMATION)
            .duree(DEFAULT_DUREE)
            .admission(DEFAULT_ADMISSION)
            .diplomeRequis(DEFAULT_DIPLOME_REQUIS)
            .ficheFormation(DEFAULT_FICHE_FORMATION)
            .ficheFormationContentType(DEFAULT_FICHE_FORMATION_CONTENT_TYPE);
        return formation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formation createUpdatedEntity(EntityManager em) {
        Formation formation = new Formation()
            .nomFormation(UPDATED_NOM_FORMATION)
            .imageFormation(UPDATED_IMAGE_FORMATION)
            .imageFormationContentType(UPDATED_IMAGE_FORMATION_CONTENT_TYPE)
            .typeFormation(UPDATED_TYPE_FORMATION)
            .duree(UPDATED_DUREE)
            .admission(UPDATED_ADMISSION)
            .diplomeRequis(UPDATED_DIPLOME_REQUIS)
            .ficheFormation(UPDATED_FICHE_FORMATION)
            .ficheFormationContentType(UPDATED_FICHE_FORMATION_CONTENT_TYPE);
        return formation;
    }

    @BeforeEach
    public void initTest() {
        formation = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFormation != null) {
            formationRepository.delete(insertedFormation);
            insertedFormation = null;
        }
    }

    @Test
    @Transactional
    void createFormation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Formation
        var returnedFormation = om.readValue(
            restFormationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formation)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Formation.class
        );

        // Validate the Formation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFormationUpdatableFieldsEquals(returnedFormation, getPersistedFormation(returnedFormation));

        insertedFormation = returnedFormation;
    }

    @Test
    @Transactional
    void createFormationWithExistingId() throws Exception {
        // Create the Formation with an existing ID
        formation.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formation)))
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormations() throws Exception {
        // Initialize the database
        insertedFormation = formationRepository.saveAndFlush(formation);

        // Get all the formationList
        restFormationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formation.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomFormation").value(hasItem(DEFAULT_NOM_FORMATION)))
            .andExpect(jsonPath("$.[*].imageFormationContentType").value(hasItem(DEFAULT_IMAGE_FORMATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageFormation").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE_FORMATION))))
            .andExpect(jsonPath("$.[*].typeFormation").value(hasItem(DEFAULT_TYPE_FORMATION.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].admission").value(hasItem(DEFAULT_ADMISSION.toString())))
            .andExpect(jsonPath("$.[*].diplomeRequis").value(hasItem(DEFAULT_DIPLOME_REQUIS.toString())))
            .andExpect(jsonPath("$.[*].ficheFormationContentType").value(hasItem(DEFAULT_FICHE_FORMATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].ficheFormation").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_FICHE_FORMATION))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFormationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(formationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFormationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(formationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFormationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(formationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFormationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(formationRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFormation() throws Exception {
        // Initialize the database
        insertedFormation = formationRepository.saveAndFlush(formation);

        // Get the formation
        restFormationMockMvc
            .perform(get(ENTITY_API_URL_ID, formation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formation.getId().intValue()))
            .andExpect(jsonPath("$.nomFormation").value(DEFAULT_NOM_FORMATION))
            .andExpect(jsonPath("$.imageFormationContentType").value(DEFAULT_IMAGE_FORMATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageFormation").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGE_FORMATION)))
            .andExpect(jsonPath("$.typeFormation").value(DEFAULT_TYPE_FORMATION.toString()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE))
            .andExpect(jsonPath("$.admission").value(DEFAULT_ADMISSION.toString()))
            .andExpect(jsonPath("$.diplomeRequis").value(DEFAULT_DIPLOME_REQUIS.toString()))
            .andExpect(jsonPath("$.ficheFormationContentType").value(DEFAULT_FICHE_FORMATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.ficheFormation").value(Base64.getEncoder().encodeToString(DEFAULT_FICHE_FORMATION)));
    }

    @Test
    @Transactional
    void getNonExistingFormation() throws Exception {
        // Get the formation
        restFormationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFormation() throws Exception {
        // Initialize the database
        insertedFormation = formationRepository.saveAndFlush(formation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formation
        Formation updatedFormation = formationRepository.findById(formation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFormation are not directly saved in db
        em.detach(updatedFormation);
        updatedFormation
            .nomFormation(UPDATED_NOM_FORMATION)
            .imageFormation(UPDATED_IMAGE_FORMATION)
            .imageFormationContentType(UPDATED_IMAGE_FORMATION_CONTENT_TYPE)
            .typeFormation(UPDATED_TYPE_FORMATION)
            .duree(UPDATED_DUREE)
            .admission(UPDATED_ADMISSION)
            .diplomeRequis(UPDATED_DIPLOME_REQUIS)
            .ficheFormation(UPDATED_FICHE_FORMATION)
            .ficheFormationContentType(UPDATED_FICHE_FORMATION_CONTENT_TYPE);

        restFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFormation))
            )
            .andExpect(status().isOk());

        // Validate the Formation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFormationToMatchAllProperties(updatedFormation);
    }

    @Test
    @Transactional
    void putNonExistingFormation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formation.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormationWithPatch() throws Exception {
        // Initialize the database
        insertedFormation = formationRepository.saveAndFlush(formation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formation using partial update
        Formation partialUpdatedFormation = new Formation();
        partialUpdatedFormation.setId(formation.getId());

        partialUpdatedFormation
            .nomFormation(UPDATED_NOM_FORMATION)
            .typeFormation(UPDATED_TYPE_FORMATION)
            .duree(UPDATED_DUREE)
            .diplomeRequis(UPDATED_DIPLOME_REQUIS);

        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormation))
            )
            .andExpect(status().isOk());

        // Validate the Formation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFormation, formation),
            getPersistedFormation(formation)
        );
    }

    @Test
    @Transactional
    void fullUpdateFormationWithPatch() throws Exception {
        // Initialize the database
        insertedFormation = formationRepository.saveAndFlush(formation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formation using partial update
        Formation partialUpdatedFormation = new Formation();
        partialUpdatedFormation.setId(formation.getId());

        partialUpdatedFormation
            .nomFormation(UPDATED_NOM_FORMATION)
            .imageFormation(UPDATED_IMAGE_FORMATION)
            .imageFormationContentType(UPDATED_IMAGE_FORMATION_CONTENT_TYPE)
            .typeFormation(UPDATED_TYPE_FORMATION)
            .duree(UPDATED_DUREE)
            .admission(UPDATED_ADMISSION)
            .diplomeRequis(UPDATED_DIPLOME_REQUIS)
            .ficheFormation(UPDATED_FICHE_FORMATION)
            .ficheFormationContentType(UPDATED_FICHE_FORMATION_CONTENT_TYPE);

        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormation))
            )
            .andExpect(status().isOk());

        // Validate the Formation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormationUpdatableFieldsEquals(partialUpdatedFormation, getPersistedFormation(partialUpdatedFormation));
    }

    @Test
    @Transactional
    void patchNonExistingFormation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(formation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormation() throws Exception {
        // Initialize the database
        insertedFormation = formationRepository.saveAndFlush(formation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the formation
        restFormationMockMvc
            .perform(delete(ENTITY_API_URL_ID, formation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return formationRepository.count();
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

    protected Formation getPersistedFormation(Formation formation) {
        return formationRepository.findById(formation.getId()).orElseThrow();
    }

    protected void assertPersistedFormationToMatchAllProperties(Formation expectedFormation) {
        assertFormationAllPropertiesEquals(expectedFormation, getPersistedFormation(expectedFormation));
    }

    protected void assertPersistedFormationToMatchUpdatableProperties(Formation expectedFormation) {
        assertFormationAllUpdatablePropertiesEquals(expectedFormation, getPersistedFormation(expectedFormation));
    }
}
