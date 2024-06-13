package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Dossier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Dossier}.
 */
public interface DossierService {
    /**
     * Save a dossier.
     *
     * @param dossier the entity to save.
     * @return the persisted entity.
     */
    Dossier save(Dossier dossier);

    /**
     * Updates a dossier.
     *
     * @param dossier the entity to update.
     * @return the persisted entity.
     */
    Dossier update(Dossier dossier);

    /**
     * Partially updates a dossier.
     *
     * @param dossier the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Dossier> partialUpdate(Dossier dossier);

    /**
     * Get all the dossiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Dossier> findAll(Pageable pageable);

    /**
     * Get all the Dossier where Demandeur is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Dossier> findAllWhereDemandeurIsNull();

    /**
     * Get all the dossiers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Dossier> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" dossier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Dossier> findOne(Long id);

    /**
     * Delete the "id" dossier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
