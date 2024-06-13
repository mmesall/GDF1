package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Etablissement;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Etablissement}.
 */
public interface EtablissementService {
    /**
     * Save a etablissement.
     *
     * @param etablissement the entity to save.
     * @return the persisted entity.
     */
    Etablissement save(Etablissement etablissement);

    /**
     * Updates a etablissement.
     *
     * @param etablissement the entity to update.
     * @return the persisted entity.
     */
    Etablissement update(Etablissement etablissement);

    /**
     * Partially updates a etablissement.
     *
     * @param etablissement the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Etablissement> partialUpdate(Etablissement etablissement);

    /**
     * Get all the etablissements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Etablissement> findAll(Pageable pageable);

    /**
     * Get the "id" etablissement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Etablissement> findOne(Long id);

    /**
     * Delete the "id" etablissement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
