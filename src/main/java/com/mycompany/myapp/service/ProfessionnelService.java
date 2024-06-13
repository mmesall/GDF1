package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Professionnel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Professionnel}.
 */
public interface ProfessionnelService {
    /**
     * Save a professionnel.
     *
     * @param professionnel the entity to save.
     * @return the persisted entity.
     */
    Professionnel save(Professionnel professionnel);

    /**
     * Updates a professionnel.
     *
     * @param professionnel the entity to update.
     * @return the persisted entity.
     */
    Professionnel update(Professionnel professionnel);

    /**
     * Partially updates a professionnel.
     *
     * @param professionnel the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Professionnel> partialUpdate(Professionnel professionnel);

    /**
     * Get all the professionnels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Professionnel> findAll(Pageable pageable);

    /**
     * Get all the Professionnel where Dossier is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Professionnel> findAllWhereDossierIsNull();
    /**
     * Get all the Professionnel where Demandeur is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Professionnel> findAllWhereDemandeurIsNull();

    /**
     * Get all the professionnels with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Professionnel> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" professionnel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Professionnel> findOne(Long id);

    /**
     * Delete the "id" professionnel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
