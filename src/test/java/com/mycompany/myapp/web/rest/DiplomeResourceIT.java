package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.DiplomeAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Diplome;
import com.mycompany.myapp.domain.enumeration.Mention;
import com.mycompany.myapp.domain.enumeration.NiveauEtude;
import com.mycompany.myapp.domain.enumeration.NomFiliere;
import com.mycompany.myapp.repository.DiplomeRepository;
import com.mycompany.myapp.service.DiplomeService;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link DiplomeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DiplomeResourceIT {

    private static final String DEFAULT_INTITULE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE = "BBBBBBBBBB";

    private static final NomFiliere DEFAULT_DOMAINE = NomFiliere.AGRI_ELEVAGE;
    private static final NomFiliere UPDATED_DOMAINE = NomFiliere.AGRICULTURE;

    private static final NiveauEtude DEFAULT_NIVEAU = NiveauEtude.CINQUIEME;
    private static final NiveauEtude UPDATED_NIVEAU = NiveauEtude.QUATRIEME;

    private static final Mention DEFAULT_MENTION = Mention.PASSABLE;
    private static final Mention UPDATED_MENTION = Mention.ASSEZ_BIEN;

    private static final String DEFAULT_ANNEE_OBTENTION = "AAAAAAAAAA";
    private static final String UPDATED_ANNEE_OBTENTION = "BBBBBBBBBB";

    private static final String DEFAULT_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_ETABLISSEMENT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/diplomes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DiplomeRepository diplomeRepository;

    @Mock
    private DiplomeRepository diplomeRepositoryMock;

    @Mock
    private DiplomeService diplomeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiplomeMockMvc;

    private Diplome diplome;

    private Diplome insertedDiplome;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diplome createEntity(EntityManager em) {
        Diplome diplome = new Diplome()
            .intitule(DEFAULT_INTITULE)
            .domaine(DEFAULT_DOMAINE)
            .niveau(DEFAULT_NIVEAU)
            .mention(DEFAULT_MENTION)
            .anneeObtention(DEFAULT_ANNEE_OBTENTION)
            .etablissement(DEFAULT_ETABLISSEMENT)
            .document(DEFAULT_DOCUMENT)
            .documentContentType(DEFAULT_DOCUMENT_CONTENT_TYPE);
        return diplome;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diplome createUpdatedEntity(EntityManager em) {
        Diplome diplome = new Diplome()
            .intitule(UPDATED_INTITULE)
            .domaine(UPDATED_DOMAINE)
            .niveau(UPDATED_NIVEAU)
            .mention(UPDATED_MENTION)
            .anneeObtention(UPDATED_ANNEE_OBTENTION)
            .etablissement(UPDATED_ETABLISSEMENT)
            .document(UPDATED_DOCUMENT)
            .documentContentType(UPDATED_DOCUMENT_CONTENT_TYPE);
        return diplome;
    }

    @BeforeEach
    public void initTest() {
        diplome = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDiplome != null) {
            diplomeRepository.delete(insertedDiplome);
            insertedDiplome = null;
        }
    }

    @Test
    @Transactional
    void createDiplome() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Diplome
        var returnedDiplome = om.readValue(
            restDiplomeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(diplome)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Diplome.class
        );

        // Validate the Diplome in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDiplomeUpdatableFieldsEquals(returnedDiplome, getPersistedDiplome(returnedDiplome));

        insertedDiplome = returnedDiplome;
    }

    @Test
    @Transactional
    void createDiplomeWithExistingId() throws Exception {
        // Create the Diplome with an existing ID
        diplome.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiplomeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(diplome)))
            .andExpect(status().isBadRequest());

        // Validate the Diplome in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDomaineIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        diplome.setDomaine(null);

        // Create the Diplome, which fails.

        restDiplomeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(diplome)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDiplomes() throws Exception {
        // Initialize the database
        insertedDiplome = diplomeRepository.saveAndFlush(diplome);

        // Get all the diplomeList
        restDiplomeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diplome.getId().intValue())))
            .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE)))
            .andExpect(jsonPath("$.[*].domaine").value(hasItem(DEFAULT_DOMAINE.toString())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU.toString())))
            .andExpect(jsonPath("$.[*].mention").value(hasItem(DEFAULT_MENTION.toString())))
            .andExpect(jsonPath("$.[*].anneeObtention").value(hasItem(DEFAULT_ANNEE_OBTENTION)))
            .andExpect(jsonPath("$.[*].etablissement").value(hasItem(DEFAULT_ETABLISSEMENT)))
            .andExpect(jsonPath("$.[*].documentContentType").value(hasItem(DEFAULT_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].document").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_DOCUMENT))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDiplomesWithEagerRelationshipsIsEnabled() throws Exception {
        when(diplomeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDiplomeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(diplomeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDiplomesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(diplomeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDiplomeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(diplomeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDiplome() throws Exception {
        // Initialize the database
        insertedDiplome = diplomeRepository.saveAndFlush(diplome);

        // Get the diplome
        restDiplomeMockMvc
            .perform(get(ENTITY_API_URL_ID, diplome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diplome.getId().intValue()))
            .andExpect(jsonPath("$.intitule").value(DEFAULT_INTITULE))
            .andExpect(jsonPath("$.domaine").value(DEFAULT_DOMAINE.toString()))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU.toString()))
            .andExpect(jsonPath("$.mention").value(DEFAULT_MENTION.toString()))
            .andExpect(jsonPath("$.anneeObtention").value(DEFAULT_ANNEE_OBTENTION))
            .andExpect(jsonPath("$.etablissement").value(DEFAULT_ETABLISSEMENT))
            .andExpect(jsonPath("$.documentContentType").value(DEFAULT_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.document").value(Base64.getEncoder().encodeToString(DEFAULT_DOCUMENT)));
    }

    @Test
    @Transactional
    void getNonExistingDiplome() throws Exception {
        // Get the diplome
        restDiplomeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDiplome() throws Exception {
        // Initialize the database
        insertedDiplome = diplomeRepository.saveAndFlush(diplome);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the diplome
        Diplome updatedDiplome = diplomeRepository.findById(diplome.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDiplome are not directly saved in db
        em.detach(updatedDiplome);
        updatedDiplome
            .intitule(UPDATED_INTITULE)
            .domaine(UPDATED_DOMAINE)
            .niveau(UPDATED_NIVEAU)
            .mention(UPDATED_MENTION)
            .anneeObtention(UPDATED_ANNEE_OBTENTION)
            .etablissement(UPDATED_ETABLISSEMENT)
            .document(UPDATED_DOCUMENT)
            .documentContentType(UPDATED_DOCUMENT_CONTENT_TYPE);

        restDiplomeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDiplome.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDiplome))
            )
            .andExpect(status().isOk());

        // Validate the Diplome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDiplomeToMatchAllProperties(updatedDiplome);
    }

    @Test
    @Transactional
    void putNonExistingDiplome() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diplome.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiplomeMockMvc
            .perform(put(ENTITY_API_URL_ID, diplome.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(diplome)))
            .andExpect(status().isBadRequest());

        // Validate the Diplome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiplome() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diplome.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiplomeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(diplome))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diplome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiplome() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diplome.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiplomeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(diplome)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Diplome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiplomeWithPatch() throws Exception {
        // Initialize the database
        insertedDiplome = diplomeRepository.saveAndFlush(diplome);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the diplome using partial update
        Diplome partialUpdatedDiplome = new Diplome();
        partialUpdatedDiplome.setId(diplome.getId());

        partialUpdatedDiplome
            .anneeObtention(UPDATED_ANNEE_OBTENTION)
            .document(UPDATED_DOCUMENT)
            .documentContentType(UPDATED_DOCUMENT_CONTENT_TYPE);

        restDiplomeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiplome.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDiplome))
            )
            .andExpect(status().isOk());

        // Validate the Diplome in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDiplomeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDiplome, diplome), getPersistedDiplome(diplome));
    }

    @Test
    @Transactional
    void fullUpdateDiplomeWithPatch() throws Exception {
        // Initialize the database
        insertedDiplome = diplomeRepository.saveAndFlush(diplome);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the diplome using partial update
        Diplome partialUpdatedDiplome = new Diplome();
        partialUpdatedDiplome.setId(diplome.getId());

        partialUpdatedDiplome
            .intitule(UPDATED_INTITULE)
            .domaine(UPDATED_DOMAINE)
            .niveau(UPDATED_NIVEAU)
            .mention(UPDATED_MENTION)
            .anneeObtention(UPDATED_ANNEE_OBTENTION)
            .etablissement(UPDATED_ETABLISSEMENT)
            .document(UPDATED_DOCUMENT)
            .documentContentType(UPDATED_DOCUMENT_CONTENT_TYPE);

        restDiplomeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiplome.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDiplome))
            )
            .andExpect(status().isOk());

        // Validate the Diplome in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDiplomeUpdatableFieldsEquals(partialUpdatedDiplome, getPersistedDiplome(partialUpdatedDiplome));
    }

    @Test
    @Transactional
    void patchNonExistingDiplome() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diplome.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiplomeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, diplome.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(diplome))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diplome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiplome() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diplome.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiplomeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(diplome))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diplome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiplome() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diplome.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiplomeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(diplome)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Diplome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiplome() throws Exception {
        // Initialize the database
        insertedDiplome = diplomeRepository.saveAndFlush(diplome);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the diplome
        restDiplomeMockMvc
            .perform(delete(ENTITY_API_URL_ID, diplome.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return diplomeRepository.count();
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

    protected Diplome getPersistedDiplome(Diplome diplome) {
        return diplomeRepository.findById(diplome.getId()).orElseThrow();
    }

    protected void assertPersistedDiplomeToMatchAllProperties(Diplome expectedDiplome) {
        assertDiplomeAllPropertiesEquals(expectedDiplome, getPersistedDiplome(expectedDiplome));
    }

    protected void assertPersistedDiplomeToMatchUpdatableProperties(Diplome expectedDiplome) {
        assertDiplomeAllUpdatablePropertiesEquals(expectedDiplome, getPersistedDiplome(expectedDiplome));
    }
}
