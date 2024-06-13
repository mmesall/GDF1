package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PriseEnCharge;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.PriseEnCharge}.
 */
public interface PriseEnChargeService {
    /**
     * Save a priseEnCharge.
     *
     * @param priseEnCharge the entity to save.
     * @return the persisted entity.
     */
    PriseEnCharge save(PriseEnCharge priseEnCharge);

    /**
     * Updates a priseEnCharge.
     *
     * @param priseEnCharge the entity to update.
     * @return the persisted entity.
     */
    PriseEnCharge update(PriseEnCharge priseEnCharge);

    /**
     * Partially updates a priseEnCharge.
     *
     * @param priseEnCharge the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PriseEnCharge> partialUpdate(PriseEnCharge priseEnCharge);

    /**
     * Get all the priseEnCharges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PriseEnCharge> findAll(Pageable pageable);

    /**
     * Get all the priseEnCharges with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PriseEnCharge> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" priseEnCharge.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PriseEnCharge> findOne(Long id);

    /**
     * Delete the "id" priseEnCharge.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
