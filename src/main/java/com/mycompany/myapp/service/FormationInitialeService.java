package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.FormationInitiale;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.FormationInitiale}.
 */
public interface FormationInitialeService {
    /**
     * Save a formationInitiale.
     *
     * @param formationInitiale the entity to save.
     * @return the persisted entity.
     */
    FormationInitiale save(FormationInitiale formationInitiale);

    /**
     * Updates a formationInitiale.
     *
     * @param formationInitiale the entity to update.
     * @return the persisted entity.
     */
    FormationInitiale update(FormationInitiale formationInitiale);

    /**
     * Partially updates a formationInitiale.
     *
     * @param formationInitiale the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FormationInitiale> partialUpdate(FormationInitiale formationInitiale);

    /**
     * Get all the formationInitiales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormationInitiale> findAll(Pageable pageable);

    /**
     * Get the "id" formationInitiale.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormationInitiale> findOne(Long id);

    /**
     * Delete the "id" formationInitiale.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
