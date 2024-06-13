package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Diplome;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Diplome}.
 */
public interface DiplomeService {
    /**
     * Save a diplome.
     *
     * @param diplome the entity to save.
     * @return the persisted entity.
     */
    Diplome save(Diplome diplome);

    /**
     * Updates a diplome.
     *
     * @param diplome the entity to update.
     * @return the persisted entity.
     */
    Diplome update(Diplome diplome);

    /**
     * Partially updates a diplome.
     *
     * @param diplome the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Diplome> partialUpdate(Diplome diplome);

    /**
     * Get all the diplomes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Diplome> findAll(Pageable pageable);

    /**
     * Get all the diplomes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Diplome> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" diplome.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Diplome> findOne(Long id);

    /**
     * Delete the "id" diplome.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
