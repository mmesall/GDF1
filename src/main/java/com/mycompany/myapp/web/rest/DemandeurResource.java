package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Demandeur;
import com.mycompany.myapp.repository.DemandeurRepository;
import com.mycompany.myapp.service.DemandeurService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Demandeur}.
 */
@RestController
@RequestMapping("/api/demandeurs")
public class DemandeurResource {

    private final Logger log = LoggerFactory.getLogger(DemandeurResource.class);

    private static final String ENTITY_NAME = "demandeur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeurService demandeurService;

    private final DemandeurRepository demandeurRepository;

    public DemandeurResource(DemandeurService demandeurService, DemandeurRepository demandeurRepository) {
        this.demandeurService = demandeurService;
        this.demandeurRepository = demandeurRepository;
    }

    /**
     * {@code POST  /demandeurs} : Create a new demandeur.
     *
     * @param demandeur the demandeur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeur, or with status {@code 400 (Bad Request)} if the demandeur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Demandeur> createDemandeur(@Valid @RequestBody Demandeur demandeur) throws URISyntaxException {
        log.debug("REST request to save Demandeur : {}", demandeur);
        if (demandeur.getId() != null) {
            throw new BadRequestAlertException("A new demandeur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        demandeur = demandeurService.save(demandeur);
        return ResponseEntity.created(new URI("/api/demandeurs/" + demandeur.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, demandeur.getId().toString()))
            .body(demandeur);
    }

    /**
     * {@code PUT  /demandeurs/:id} : Updates an existing demandeur.
     *
     * @param id the id of the demandeur to save.
     * @param demandeur the demandeur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeur,
     * or with status {@code 400 (Bad Request)} if the demandeur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Demandeur> updateDemandeur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Demandeur demandeur
    ) throws URISyntaxException {
        log.debug("REST request to update Demandeur : {}, {}", id, demandeur);
        if (demandeur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        demandeur = demandeurService.update(demandeur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeur.getId().toString()))
            .body(demandeur);
    }

    /**
     * {@code PATCH  /demandeurs/:id} : Partial updates given fields of an existing demandeur, field will ignore if it is null
     *
     * @param id the id of the demandeur to save.
     * @param demandeur the demandeur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeur,
     * or with status {@code 400 (Bad Request)} if the demandeur is not valid,
     * or with status {@code 404 (Not Found)} if the demandeur is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Demandeur> partialUpdateDemandeur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Demandeur demandeur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Demandeur partially : {}, {}", id, demandeur);
        if (demandeur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Demandeur> result = demandeurService.partialUpdate(demandeur);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeur.getId().toString())
        );
    }

    /**
     * {@code GET  /demandeurs} : get all the demandeurs.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeurs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Demandeur>> getAllDemandeurs(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Demandeurs");
        Page<Demandeur> page;
        if (eagerload) {
            page = demandeurService.findAllWithEagerRelationships(pageable);
        } else {
            page = demandeurService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demandeurs/:id} : get the "id" demandeur.
     *
     * @param id the id of the demandeur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Demandeur> getDemandeur(@PathVariable("id") Long id) {
        log.debug("REST request to get Demandeur : {}", id);
        Optional<Demandeur> demandeur = demandeurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeur);
    }

    /**
     * {@code DELETE  /demandeurs/:id} : delete the "id" demandeur.
     *
     * @param id the id of the demandeur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemandeur(@PathVariable("id") Long id) {
        log.debug("REST request to delete Demandeur : {}", id);
        demandeurService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
