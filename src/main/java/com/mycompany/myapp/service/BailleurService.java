package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Bailleur;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Bailleur}.
 */
public interface BailleurService {
    /**
     * Save a bailleur.
     *
     * @param bailleur the entity to save.
     * @return the persisted entity.
     */
    Bailleur save(Bailleur bailleur);

    /**
     * Updates a bailleur.
     *
     * @param bailleur the entity to update.
     * @return the persisted entity.
     */
    Bailleur update(Bailleur bailleur);

    /**
     * Partially updates a bailleur.
     *
     * @param bailleur the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Bailleur> partialUpdate(Bailleur bailleur);

    /**
     * Get all the bailleurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Bailleur> findAll(Pageable pageable);

    /**
     * Get the "id" bailleur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bailleur> findOne(Long id);

    /**
     * Delete the "id" bailleur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
