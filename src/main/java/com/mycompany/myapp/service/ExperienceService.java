package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Experience;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Experience}.
 */
public interface ExperienceService {
    /**
     * Save a experience.
     *
     * @param experience the entity to save.
     * @return the persisted entity.
     */
    Experience save(Experience experience);

    /**
     * Updates a experience.
     *
     * @param experience the entity to update.
     * @return the persisted entity.
     */
    Experience update(Experience experience);

    /**
     * Partially updates a experience.
     *
     * @param experience the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Experience> partialUpdate(Experience experience);

    /**
     * Get all the experiences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Experience> findAll(Pageable pageable);

    /**
     * Get all the experiences with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Experience> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" experience.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Experience> findOne(Long id);

    /**
     * Delete the "id" experience.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
