package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Demandeur;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Demandeur}.
 */
public interface DemandeurService {
    /**
     * Save a demandeur.
     *
     * @param demandeur the entity to save.
     * @return the persisted entity.
     */
    Demandeur save(Demandeur demandeur);

    /**
     * Updates a demandeur.
     *
     * @param demandeur the entity to update.
     * @return the persisted entity.
     */
    Demandeur update(Demandeur demandeur);

    /**
     * Partially updates a demandeur.
     *
     * @param demandeur the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Demandeur> partialUpdate(Demandeur demandeur);

    /**
     * Get all the demandeurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Demandeur> findAll(Pageable pageable);

    /**
     * Get all the demandeurs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Demandeur> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" demandeur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Demandeur> findOne(Long id);

    /**
     * Delete the "id" demandeur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
