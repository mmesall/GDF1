package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Formation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Formation}.
 */
public interface FormationService {
    /**
     * Save a formation.
     *
     * @param formation the entity to save.
     * @return the persisted entity.
     */
    Formation save(Formation formation);

    /**
     * Updates a formation.
     *
     * @param formation the entity to update.
     * @return the persisted entity.
     */
    Formation update(Formation formation);

    /**
     * Partially updates a formation.
     *
     * @param formation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Formation> partialUpdate(Formation formation);

    /**
     * Get all the formations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Formation> findAll(Pageable pageable);

    /**
     * Get all the Formation where PriseEnCharge is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Formation> findAllWherePriseEnChargeIsNull();
    /**
     * Get all the Formation where FormationInitiale is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Formation> findAllWhereFormationInitialeIsNull();
    /**
     * Get all the Formation where FormationContinue is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Formation> findAllWhereFormationContinueIsNull();
    /**
     * Get all the Formation where Concours is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Formation> findAllWhereConcoursIsNull();

    /**
     * Get all the formations with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Formation> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" formation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Formation> findOne(Long id);

    /**
     * Delete the "id" formation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
