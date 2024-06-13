package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CandidatureP;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CandidatureP}.
 */
public interface CandidaturePService {
    /**
     * Save a candidatureP.
     *
     * @param candidatureP the entity to save.
     * @return the persisted entity.
     */
    CandidatureP save(CandidatureP candidatureP);

    /**
     * Updates a candidatureP.
     *
     * @param candidatureP the entity to update.
     * @return the persisted entity.
     */
    CandidatureP update(CandidatureP candidatureP);

    /**
     * Partially updates a candidatureP.
     *
     * @param candidatureP the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CandidatureP> partialUpdate(CandidatureP candidatureP);

    /**
     * Get all the candidaturePS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CandidatureP> findAll(Pageable pageable);

    /**
     * Get all the candidaturePS with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CandidatureP> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" candidatureP.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CandidatureP> findOne(Long id);

    /**
     * Delete the "id" candidatureP.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
