package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ServiceMFPAIAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ServiceMFPAI;
import com.mycompany.myapp.repository.ServiceMFPAIRepository;
import jakarta.persistence.EntityManager;
import java.util.Base64;
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
 * Integration tests for the {@link ServiceMFPAIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceMFPAIResourceIT {

    private static final byte[] DEFAULT_IMAGE_SERVICE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_SERVICE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_SERVICE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_SERVICE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOM_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_SERVICE = "BBBBBBBBBB";

    private static final String DEFAULT_CHEF_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_CHEF_SERVICE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/service-mfpais";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiceMFPAIRepository serviceMFPAIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceMFPAIMockMvc;

    private ServiceMFPAI serviceMFPAI;

    private ServiceMFPAI insertedServiceMFPAI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceMFPAI createEntity(EntityManager em) {
        ServiceMFPAI serviceMFPAI = new ServiceMFPAI()
            .imageService(DEFAULT_IMAGE_SERVICE)
            .imageServiceContentType(DEFAULT_IMAGE_SERVICE_CONTENT_TYPE)
            .nomService(DEFAULT_NOM_SERVICE)
            .chefService(DEFAULT_CHEF_SERVICE)
            .description(DEFAULT_DESCRIPTION);
        return serviceMFPAI;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceMFPAI createUpdatedEntity(EntityManager em) {
        ServiceMFPAI serviceMFPAI = new ServiceMFPAI()
            .imageService(UPDATED_IMAGE_SERVICE)
            .imageServiceContentType(UPDATED_IMAGE_SERVICE_CONTENT_TYPE)
            .nomService(UPDATED_NOM_SERVICE)
            .chefService(UPDATED_CHEF_SERVICE)
            .description(UPDATED_DESCRIPTION);
        return serviceMFPAI;
    }

    @BeforeEach
    public void initTest() {
        serviceMFPAI = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedServiceMFPAI != null) {
            serviceMFPAIRepository.delete(insertedServiceMFPAI);
            insertedServiceMFPAI = null;
        }
    }

    @Test
    @Transactional
    void createServiceMFPAI() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServiceMFPAI
        var returnedServiceMFPAI = om.readValue(
            restServiceMFPAIMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceMFPAI)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceMFPAI.class
        );

        // Validate the ServiceMFPAI in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServiceMFPAIUpdatableFieldsEquals(returnedServiceMFPAI, getPersistedServiceMFPAI(returnedServiceMFPAI));

        insertedServiceMFPAI = returnedServiceMFPAI;
    }

    @Test
    @Transactional
    void createServiceMFPAIWithExistingId() throws Exception {
        // Create the ServiceMFPAI with an existing ID
        serviceMFPAI.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceMFPAIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceMFPAI)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceMFPAI in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkChefServiceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceMFPAI.setChefService(null);

        // Create the ServiceMFPAI, which fails.

        restServiceMFPAIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceMFPAI)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServiceMFPAIS() throws Exception {
        // Initialize the database
        insertedServiceMFPAI = serviceMFPAIRepository.saveAndFlush(serviceMFPAI);

        // Get all the serviceMFPAIList
        restServiceMFPAIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceMFPAI.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageServiceContentType").value(hasItem(DEFAULT_IMAGE_SERVICE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageService").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE_SERVICE))))
            .andExpect(jsonPath("$.[*].nomService").value(hasItem(DEFAULT_NOM_SERVICE)))
            .andExpect(jsonPath("$.[*].chefService").value(hasItem(DEFAULT_CHEF_SERVICE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    void getServiceMFPAI() throws Exception {
        // Initialize the database
        insertedServiceMFPAI = serviceMFPAIRepository.saveAndFlush(serviceMFPAI);

        // Get the serviceMFPAI
        restServiceMFPAIMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceMFPAI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceMFPAI.getId().intValue()))
            .andExpect(jsonPath("$.imageServiceContentType").value(DEFAULT_IMAGE_SERVICE_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageService").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGE_SERVICE)))
            .andExpect(jsonPath("$.nomService").value(DEFAULT_NOM_SERVICE))
            .andExpect(jsonPath("$.chefService").value(DEFAULT_CHEF_SERVICE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingServiceMFPAI() throws Exception {
        // Get the serviceMFPAI
        restServiceMFPAIMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceMFPAI() throws Exception {
        // Initialize the database
        insertedServiceMFPAI = serviceMFPAIRepository.saveAndFlush(serviceMFPAI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceMFPAI
        ServiceMFPAI updatedServiceMFPAI = serviceMFPAIRepository.findById(serviceMFPAI.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServiceMFPAI are not directly saved in db
        em.detach(updatedServiceMFPAI);
        updatedServiceMFPAI
            .imageService(UPDATED_IMAGE_SERVICE)
            .imageServiceContentType(UPDATED_IMAGE_SERVICE_CONTENT_TYPE)
            .nomService(UPDATED_NOM_SERVICE)
            .chefService(UPDATED_CHEF_SERVICE)
            .description(UPDATED_DESCRIPTION);

        restServiceMFPAIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServiceMFPAI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServiceMFPAI))
            )
            .andExpect(status().isOk());

        // Validate the ServiceMFPAI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceMFPAIToMatchAllProperties(updatedServiceMFPAI);
    }

    @Test
    @Transactional
    void putNonExistingServiceMFPAI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceMFPAI.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceMFPAIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceMFPAI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceMFPAI))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceMFPAI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceMFPAI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceMFPAI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMFPAIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceMFPAI))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceMFPAI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceMFPAI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceMFPAI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMFPAIMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceMFPAI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceMFPAI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceMFPAIWithPatch() throws Exception {
        // Initialize the database
        insertedServiceMFPAI = serviceMFPAIRepository.saveAndFlush(serviceMFPAI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceMFPAI using partial update
        ServiceMFPAI partialUpdatedServiceMFPAI = new ServiceMFPAI();
        partialUpdatedServiceMFPAI.setId(serviceMFPAI.getId());

        partialUpdatedServiceMFPAI
            .imageService(UPDATED_IMAGE_SERVICE)
            .imageServiceContentType(UPDATED_IMAGE_SERVICE_CONTENT_TYPE)
            .nomService(UPDATED_NOM_SERVICE)
            .chefService(UPDATED_CHEF_SERVICE)
            .description(UPDATED_DESCRIPTION);

        restServiceMFPAIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceMFPAI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceMFPAI))
            )
            .andExpect(status().isOk());

        // Validate the ServiceMFPAI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceMFPAIUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServiceMFPAI, serviceMFPAI),
            getPersistedServiceMFPAI(serviceMFPAI)
        );
    }

    @Test
    @Transactional
    void fullUpdateServiceMFPAIWithPatch() throws Exception {
        // Initialize the database
        insertedServiceMFPAI = serviceMFPAIRepository.saveAndFlush(serviceMFPAI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceMFPAI using partial update
        ServiceMFPAI partialUpdatedServiceMFPAI = new ServiceMFPAI();
        partialUpdatedServiceMFPAI.setId(serviceMFPAI.getId());

        partialUpdatedServiceMFPAI
            .imageService(UPDATED_IMAGE_SERVICE)
            .imageServiceContentType(UPDATED_IMAGE_SERVICE_CONTENT_TYPE)
            .nomService(UPDATED_NOM_SERVICE)
            .chefService(UPDATED_CHEF_SERVICE)
            .description(UPDATED_DESCRIPTION);

        restServiceMFPAIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceMFPAI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceMFPAI))
            )
            .andExpect(status().isOk());

        // Validate the ServiceMFPAI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceMFPAIUpdatableFieldsEquals(partialUpdatedServiceMFPAI, getPersistedServiceMFPAI(partialUpdatedServiceMFPAI));
    }

    @Test
    @Transactional
    void patchNonExistingServiceMFPAI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceMFPAI.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceMFPAIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceMFPAI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceMFPAI))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceMFPAI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceMFPAI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceMFPAI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMFPAIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceMFPAI))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceMFPAI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceMFPAI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceMFPAI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMFPAIMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceMFPAI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceMFPAI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceMFPAI() throws Exception {
        // Initialize the database
        insertedServiceMFPAI = serviceMFPAIRepository.saveAndFlush(serviceMFPAI);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the serviceMFPAI
        restServiceMFPAIMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceMFPAI.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceMFPAIRepository.count();
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

    protected ServiceMFPAI getPersistedServiceMFPAI(ServiceMFPAI serviceMFPAI) {
        return serviceMFPAIRepository.findById(serviceMFPAI.getId()).orElseThrow();
    }

    protected void assertPersistedServiceMFPAIToMatchAllProperties(ServiceMFPAI expectedServiceMFPAI) {
        assertServiceMFPAIAllPropertiesEquals(expectedServiceMFPAI, getPersistedServiceMFPAI(expectedServiceMFPAI));
    }

    protected void assertPersistedServiceMFPAIToMatchUpdatableProperties(ServiceMFPAI expectedServiceMFPAI) {
        assertServiceMFPAIAllUpdatablePropertiesEquals(expectedServiceMFPAI, getPersistedServiceMFPAI(expectedServiceMFPAI));
    }
}
