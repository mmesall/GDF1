package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ExperienceAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Experience;
import com.mycompany.myapp.repository.ExperienceRepository;
import com.mycompany.myapp.service.ExperienceService;
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
 * Integration tests for the {@link ExperienceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ExperienceResourceIT {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOM_ENTREPRISE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ENTREPRISE = "BBBBBBBBBB";

    private static final String DEFAULT_POSTE_OCCUPE = "AAAAAAAAAA";
    private static final String UPDATED_POSTE_OCCUPE = "BBBBBBBBBB";

    private static final String DEFAULT_MISSION = "AAAAAAAAAA";
    private static final String UPDATED_MISSION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/experiences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Mock
    private ExperienceRepository experienceRepositoryMock;

    @Mock
    private ExperienceService experienceServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExperienceMockMvc;

    private Experience experience;

    private Experience insertedExperience;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Experience createEntity(EntityManager em) {
        Experience experience = new Experience()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .nomEntreprise(DEFAULT_NOM_ENTREPRISE)
            .posteOccupe(DEFAULT_POSTE_OCCUPE)
            .mission(DEFAULT_MISSION);
        return experience;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Experience createUpdatedEntity(EntityManager em) {
        Experience experience = new Experience()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .nomEntreprise(UPDATED_NOM_ENTREPRISE)
            .posteOccupe(UPDATED_POSTE_OCCUPE)
            .mission(UPDATED_MISSION);
        return experience;
    }

    @BeforeEach
    public void initTest() {
        experience = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedExperience != null) {
            experienceRepository.delete(insertedExperience);
            insertedExperience = null;
        }
    }

    @Test
    @Transactional
    void createExperience() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Experience
        var returnedExperience = om.readValue(
            restExperienceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(experience)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Experience.class
        );

        // Validate the Experience in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertExperienceUpdatableFieldsEquals(returnedExperience, getPersistedExperience(returnedExperience));

        insertedExperience = returnedExperience;
    }

    @Test
    @Transactional
    void createExperienceWithExistingId() throws Exception {
        // Create the Experience with an existing ID
        experience.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperienceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(experience)))
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateDebutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        experience.setDateDebut(null);

        // Create the Experience, which fails.

        restExperienceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(experience)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateFinIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        experience.setDateFin(null);

        // Create the Experience, which fails.

        restExperienceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(experience)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomEntrepriseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        experience.setNomEntreprise(null);

        // Create the Experience, which fails.

        restExperienceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(experience)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPosteOccupeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        experience.setPosteOccupe(null);

        // Create the Experience, which fails.

        restExperienceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(experience)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExperiences() throws Exception {
        // Initialize the database
        insertedExperience = experienceRepository.saveAndFlush(experience);

        // Get all the experienceList
        restExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experience.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].nomEntreprise").value(hasItem(DEFAULT_NOM_ENTREPRISE)))
            .andExpect(jsonPath("$.[*].posteOccupe").value(hasItem(DEFAULT_POSTE_OCCUPE)))
            .andExpect(jsonPath("$.[*].mission").value(hasItem(DEFAULT_MISSION.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllExperiencesWithEagerRelationshipsIsEnabled() throws Exception {
        when(experienceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restExperienceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(experienceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllExperiencesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(experienceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restExperienceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(experienceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getExperience() throws Exception {
        // Initialize the database
        insertedExperience = experienceRepository.saveAndFlush(experience);

        // Get the experience
        restExperienceMockMvc
            .perform(get(ENTITY_API_URL_ID, experience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(experience.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.nomEntreprise").value(DEFAULT_NOM_ENTREPRISE))
            .andExpect(jsonPath("$.posteOccupe").value(DEFAULT_POSTE_OCCUPE))
            .andExpect(jsonPath("$.mission").value(DEFAULT_MISSION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingExperience() throws Exception {
        // Get the experience
        restExperienceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExperience() throws Exception {
        // Initialize the database
        insertedExperience = experienceRepository.saveAndFlush(experience);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the experience
        Experience updatedExperience = experienceRepository.findById(experience.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedExperience are not directly saved in db
        em.detach(updatedExperience);
        updatedExperience
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .nomEntreprise(UPDATED_NOM_ENTREPRISE)
            .posteOccupe(UPDATED_POSTE_OCCUPE)
            .mission(UPDATED_MISSION);

        restExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedExperience.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedExperience))
            )
            .andExpect(status().isOk());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedExperienceToMatchAllProperties(updatedExperience);
    }

    @Test
    @Transactional
    void putNonExistingExperience() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        experience.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, experience.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(experience))
            )
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExperience() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        experience.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(experience))
            )
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExperience() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        experience.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(experience)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExperienceWithPatch() throws Exception {
        // Initialize the database
        insertedExperience = experienceRepository.saveAndFlush(experience);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the experience using partial update
        Experience partialUpdatedExperience = new Experience();
        partialUpdatedExperience.setId(experience.getId());

        partialUpdatedExperience.dateFin(UPDATED_DATE_FIN).nomEntreprise(UPDATED_NOM_ENTREPRISE);

        restExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExperience))
            )
            .andExpect(status().isOk());

        // Validate the Experience in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExperienceUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedExperience, experience),
            getPersistedExperience(experience)
        );
    }

    @Test
    @Transactional
    void fullUpdateExperienceWithPatch() throws Exception {
        // Initialize the database
        insertedExperience = experienceRepository.saveAndFlush(experience);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the experience using partial update
        Experience partialUpdatedExperience = new Experience();
        partialUpdatedExperience.setId(experience.getId());

        partialUpdatedExperience
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .nomEntreprise(UPDATED_NOM_ENTREPRISE)
            .posteOccupe(UPDATED_POSTE_OCCUPE)
            .mission(UPDATED_MISSION);

        restExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExperience))
            )
            .andExpect(status().isOk());

        // Validate the Experience in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExperienceUpdatableFieldsEquals(partialUpdatedExperience, getPersistedExperience(partialUpdatedExperience));
    }

    @Test
    @Transactional
    void patchNonExistingExperience() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        experience.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, experience.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(experience))
            )
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExperience() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        experience.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(experience))
            )
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExperience() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        experience.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(experience)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Experience in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExperience() throws Exception {
        // Initialize the database
        insertedExperience = experienceRepository.saveAndFlush(experience);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the experience
        restExperienceMockMvc
            .perform(delete(ENTITY_API_URL_ID, experience.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return experienceRepository.count();
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

    protected Experience getPersistedExperience(Experience experience) {
        return experienceRepository.findById(experience.getId()).orElseThrow();
    }

    protected void assertPersistedExperienceToMatchAllProperties(Experience expectedExperience) {
        assertExperienceAllPropertiesEquals(expectedExperience, getPersistedExperience(expectedExperience));
    }

    protected void assertPersistedExperienceToMatchUpdatableProperties(Experience expectedExperience) {
        assertExperienceAllUpdatablePropertiesEquals(expectedExperience, getPersistedExperience(expectedExperience));
    }
}
