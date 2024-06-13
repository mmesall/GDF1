package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.FormationContinue;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.FormationContinue}.
 */
public interface FormationContinueService {
    /**
     * Save a formationContinue.
     *
     * @param formationContinue the entity to save.
     * @return the persisted entity.
     */
    FormationContinue save(FormationContinue formationContinue);

    /**
     * Updates a formationContinue.
     *
     * @param formationContinue the entity to update.
     * @return the persisted entity.
     */
    FormationContinue update(FormationContinue formationContinue);

    /**
     * Partially updates a formationContinue.
     *
     * @param formationContinue the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FormationContinue> partialUpdate(FormationContinue formationContinue);

    /**
     * Get all the formationContinues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormationContinue> findAll(Pageable pageable);

    /**
     * Get the "id" formationContinue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormationContinue> findOne(Long id);

    /**
     * Delete the "id" formationContinue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
