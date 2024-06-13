package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.DemandeurAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Demandeur;
import com.mycompany.myapp.domain.enumeration.Profil;
import com.mycompany.myapp.domain.enumeration.Sexe;
import com.mycompany.myapp.repository.DemandeurRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.DemandeurService;
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
 * Integration tests for the {@link DemandeurResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DemandeurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISS = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_NAISS = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISS = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.HOMME;
    private static final Sexe UPDATED_SEXE = Sexe.FEMME;

    private static final Long DEFAULT_TELEPHONE = 1L;
    private static final Long UPDATED_TELEPHONE = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Profil DEFAULT_PROFIL = Profil.ELEVE;
    private static final Profil UPDATED_PROFIL = Profil.ETUDIANT;

    private static final String ENTITY_API_URL = "/api/demandeurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DemandeurRepository demandeurRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private DemandeurRepository demandeurRepositoryMock;

    @Mock
    private DemandeurService demandeurServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeurMockMvc;

    private Demandeur demandeur;

    private Demandeur insertedDemandeur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demandeur createEntity(EntityManager em) {
        Demandeur demandeur = new Demandeur()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .dateNaiss(DEFAULT_DATE_NAISS)
            .lieuNaiss(DEFAULT_LIEU_NAISS)
            .sexe(DEFAULT_SEXE)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL)
            .profil(DEFAULT_PROFIL);
        return demandeur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demandeur createUpdatedEntity(EntityManager em) {
        Demandeur demandeur = new Demandeur()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .sexe(UPDATED_SEXE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .profil(UPDATED_PROFIL);
        return demandeur;
    }

    @BeforeEach
    public void initTest() {
        demandeur = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDemandeur != null) {
            demandeurRepository.delete(insertedDemandeur);
            insertedDemandeur = null;
        }
    }

    @Test
    @Transactional
    void createDemandeur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Demandeur
        var returnedDemandeur = om.readValue(
            restDemandeurMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandeur)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Demandeur.class
        );

        // Validate the Demandeur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDemandeurUpdatableFieldsEquals(returnedDemandeur, getPersistedDemandeur(returnedDemandeur));

        insertedDemandeur = returnedDemandeur;
    }

    @Test
    @Transactional
    void createDemandeurWithExistingId() throws Exception {
        // Create the Demandeur with an existing ID
        demandeur.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandeur)))
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemandeurs() throws Exception {
        // Initialize the database
        insertedDemandeur = demandeurRepository.saveAndFlush(demandeur);

        // Get all the demandeurList
        restDemandeurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].dateNaiss").value(hasItem(DEFAULT_DATE_NAISS.toString())))
            .andExpect(jsonPath("$.[*].lieuNaiss").value(hasItem(DEFAULT_LIEU_NAISS)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].profil").value(hasItem(DEFAULT_PROFIL.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDemandeursWithEagerRelationshipsIsEnabled() throws Exception {
        when(demandeurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDemandeurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(demandeurServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDemandeursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(demandeurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDemandeurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(demandeurRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDemandeur() throws Exception {
        // Initialize the database
        insertedDemandeur = demandeurRepository.saveAndFlush(demandeur);

        // Get the demandeur
        restDemandeurMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.dateNaiss").value(DEFAULT_DATE_NAISS.toString()))
            .andExpect(jsonPath("$.lieuNaiss").value(DEFAULT_LIEU_NAISS))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.profil").value(DEFAULT_PROFIL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDemandeur() throws Exception {
        // Get the demandeur
        restDemandeurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDemandeur() throws Exception {
        // Initialize the database
        insertedDemandeur = demandeurRepository.saveAndFlush(demandeur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demandeur
        Demandeur updatedDemandeur = demandeurRepository.findById(demandeur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDemandeur are not directly saved in db
        em.detach(updatedDemandeur);
        updatedDemandeur
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .sexe(UPDATED_SEXE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .profil(UPDATED_PROFIL);

        restDemandeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemandeur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDemandeur))
            )
            .andExpect(status().isOk());

        // Validate the Demandeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDemandeurToMatchAllProperties(updatedDemandeur);
    }

    @Test
    @Transactional
    void putNonExistingDemandeur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandeur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeur.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandeur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(demandeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandeur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandeur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demandeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeurWithPatch() throws Exception {
        // Initialize the database
        insertedDemandeur = demandeurRepository.saveAndFlush(demandeur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demandeur using partial update
        Demandeur partialUpdatedDemandeur = new Demandeur();
        partialUpdatedDemandeur.setId(demandeur.getId());

        partialUpdatedDemandeur.prenom(UPDATED_PRENOM).dateNaiss(UPDATED_DATE_NAISS).lieuNaiss(UPDATED_LIEU_NAISS);

        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDemandeur))
            )
            .andExpect(status().isOk());

        // Validate the Demandeur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDemandeurUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDemandeur, demandeur),
            getPersistedDemandeur(demandeur)
        );
    }

    @Test
    @Transactional
    void fullUpdateDemandeurWithPatch() throws Exception {
        // Initialize the database
        insertedDemandeur = demandeurRepository.saveAndFlush(demandeur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demandeur using partial update
        Demandeur partialUpdatedDemandeur = new Demandeur();
        partialUpdatedDemandeur.setId(demandeur.getId());

        partialUpdatedDemandeur
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .sexe(UPDATED_SEXE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .profil(UPDATED_PROFIL);

        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDemandeur))
            )
            .andExpect(status().isOk());

        // Validate the Demandeur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDemandeurUpdatableFieldsEquals(partialUpdatedDemandeur, getPersistedDemandeur(partialUpdatedDemandeur));
    }

    @Test
    @Transactional
    void patchNonExistingDemandeur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandeur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(demandeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandeur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(demandeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandeur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(demandeur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demandeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeur() throws Exception {
        // Initialize the database
        insertedDemandeur = demandeurRepository.saveAndFlush(demandeur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the demandeur
        restDemandeurMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return demandeurRepository.count();
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

    protected Demandeur getPersistedDemandeur(Demandeur demandeur) {
        return demandeurRepository.findById(demandeur.getId()).orElseThrow();
    }

    protected void assertPersistedDemandeurToMatchAllProperties(Demandeur expectedDemandeur) {
        assertDemandeurAllPropertiesEquals(expectedDemandeur, getPersistedDemandeur(expectedDemandeur));
    }

    protected void assertPersistedDemandeurToMatchUpdatableProperties(Demandeur expectedDemandeur) {
        assertDemandeurAllUpdatablePropertiesEquals(expectedDemandeur, getPersistedDemandeur(expectedDemandeur));
    }
}
