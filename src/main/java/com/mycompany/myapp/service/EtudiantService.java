package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Etudiant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Etudiant}.
 */
public interface EtudiantService {
    /**
     * Save a etudiant.
     *
     * @param etudiant the entity to save.
     * @return the persisted entity.
     */
    Etudiant save(Etudiant etudiant);

    /**
     * Updates a etudiant.
     *
     * @param etudiant the entity to update.
     * @return the persisted entity.
     */
    Etudiant update(Etudiant etudiant);

    /**
     * Partially updates a etudiant.
     *
     * @param etudiant the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Etudiant> partialUpdate(Etudiant etudiant);

    /**
     * Get all the etudiants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Etudiant> findAll(Pageable pageable);

    /**
     * Get all the Etudiant where Dossier is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Etudiant> findAllWhereDossierIsNull();
    /**
     * Get all the Etudiant where Demandeur is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Etudiant> findAllWhereDemandeurIsNull();

    /**
     * Get all the etudiants with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Etudiant> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" etudiant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Etudiant> findOne(Long id);

    /**
     * Delete the "id" etudiant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
