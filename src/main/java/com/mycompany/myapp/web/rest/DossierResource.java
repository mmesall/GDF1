package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Dossier;
import com.mycompany.myapp.repository.DossierRepository;
import com.mycompany.myapp.service.DossierService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Dossier}.
 */
@RestController
@RequestMapping("/api/dossiers")
public class DossierResource {

    private final Logger log = LoggerFactory.getLogger(DossierResource.class);

    private static final String ENTITY_NAME = "dossier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DossierService dossierService;

    private final DossierRepository dossierRepository;

    public DossierResource(DossierService dossierService, DossierRepository dossierRepository) {
        this.dossierService = dossierService;
        this.dossierRepository = dossierRepository;
    }

    /**
     * {@code POST  /dossiers} : Create a new dossier.
     *
     * @param dossier the dossier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dossier, or with status {@code 400 (Bad Request)} if the dossier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Dossier> createDossier(@Valid @RequestBody Dossier dossier) throws URISyntaxException {
        log.debug("REST request to save Dossier : {}", dossier);
        if (dossier.getId() != null) {
            throw new BadRequestAlertException("A new dossier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dossier = dossierService.save(dossier);
        return ResponseEntity.created(new URI("/api/dossiers/" + dossier.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dossier.getId().toString()))
            .body(dossier);
    }

    /**
     * {@code PUT  /dossiers/:id} : Updates an existing dossier.
     *
     * @param id the id of the dossier to save.
     * @param dossier the dossier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dossier,
     * or with status {@code 400 (Bad Request)} if the dossier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dossier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Dossier> updateDossier(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Dossier dossier
    ) throws URISyntaxException {
        log.debug("REST request to update Dossier : {}, {}", id, dossier);
        if (dossier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dossier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dossierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        dossier = dossierService.update(dossier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dossier.getId().toString()))
            .body(dossier);
    }

    /**
     * {@code PATCH  /dossiers/:id} : Partial updates given fields of an existing dossier, field will ignore if it is null
     *
     * @param id the id of the dossier to save.
     * @param dossier the dossier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dossier,
     * or with status {@code 400 (Bad Request)} if the dossier is not valid,
     * or with status {@code 404 (Not Found)} if the dossier is not found,
     * or with status {@code 500 (Internal Server Error)} if the dossier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Dossier> partialUpdateDossier(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Dossier dossier
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dossier partially : {}, {}", id, dossier);
        if (dossier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dossier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dossierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Dossier> result = dossierService.partialUpdate(dossier);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dossier.getId().toString())
        );
    }

    /**
     * {@code GET  /dossiers} : get all the dossiers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dossiers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Dossier>> getAllDossiers(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("demandeur-is-null".equals(filter)) {
            log.debug("REST request to get all Dossiers where demandeur is null");
            return new ResponseEntity<>(dossierService.findAllWhereDemandeurIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Dossiers");
        Page<Dossier> page;
        if (eagerload) {
            page = dossierService.findAllWithEagerRelationships(pageable);
        } else {
            page = dossierService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dossiers/:id} : get the "id" dossier.
     *
     * @param id the id of the dossier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dossier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Dossier> getDossier(@PathVariable("id") Long id) {
        log.debug("REST request to get Dossier : {}", id);
        Optional<Dossier> dossier = dossierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dossier);
    }

    /**
     * {@code DELETE  /dossiers/:id} : delete the "id" dossier.
     *
     * @param id the id of the dossier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDossier(@PathVariable("id") Long id) {
        log.debug("REST request to delete Dossier : {}", id);
        dossierService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
