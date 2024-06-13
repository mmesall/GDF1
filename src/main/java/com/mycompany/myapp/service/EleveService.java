package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Eleve;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Eleve}.
 */
public interface EleveService {
    /**
     * Save a eleve.
     *
     * @param eleve the entity to save.
     * @return the persisted entity.
     */
    Eleve save(Eleve eleve);

    /**
     * Updates a eleve.
     *
     * @param eleve the entity to update.
     * @return the persisted entity.
     */
    Eleve update(Eleve eleve);

    /**
     * Partially updates a eleve.
     *
     * @param eleve the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Eleve> partialUpdate(Eleve eleve);

    /**
     * Get all the eleves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Eleve> findAll(Pageable pageable);

    /**
     * Get all the Eleve where Dossier is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Eleve> findAllWhereDossierIsNull();
    /**
     * Get all the Eleve where Demandeur is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Eleve> findAllWhereDemandeurIsNull();

    /**
     * Get all the eleves with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Eleve> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" eleve.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Eleve> findOne(Long id);

    /**
     * Delete the "id" eleve.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
