package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CandidatureE;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CandidatureE}.
 */
public interface CandidatureEService {
    /**
     * Save a candidatureE.
     *
     * @param candidatureE the entity to save.
     * @return the persisted entity.
     */
    CandidatureE save(CandidatureE candidatureE);

    /**
     * Updates a candidatureE.
     *
     * @param candidatureE the entity to update.
     * @return the persisted entity.
     */
    CandidatureE update(CandidatureE candidatureE);

    /**
     * Partially updates a candidatureE.
     *
     * @param candidatureE the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CandidatureE> partialUpdate(CandidatureE candidatureE);

    /**
     * Get all the candidatureES.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CandidatureE> findAll(Pageable pageable);

    /**
     * Get all the candidatureES with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CandidatureE> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" candidatureE.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CandidatureE> findOne(Long id);

    /**
     * Delete the "id" candidatureE.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
