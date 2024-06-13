package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Formation;
import com.mycompany.myapp.repository.FormationRepository;
import com.mycompany.myapp.service.FormationService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Formation}.
 */
@RestController
@RequestMapping("/api/formations")
public class FormationResource {

    private final Logger log = LoggerFactory.getLogger(FormationResource.class);

    private static final String ENTITY_NAME = "formation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormationService formationService;

    private final FormationRepository formationRepository;

    public FormationResource(FormationService formationService, FormationRepository formationRepository) {
        this.formationService = formationService;
        this.formationRepository = formationRepository;
    }

    /**
     * {@code POST  /formations} : Create a new formation.
     *
     * @param formation the formation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formation, or with status {@code 400 (Bad Request)} if the formation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Formation> createFormation(@Valid @RequestBody Formation formation) throws URISyntaxException {
        log.debug("REST request to save Formation : {}", formation);
        if (formation.getId() != null) {
            throw new BadRequestAlertException("A new formation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        formation = formationService.save(formation);
        return ResponseEntity.created(new URI("/api/formations/" + formation.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, formation.getId().toString()))
            .body(formation);
    }

    /**
     * {@code PUT  /formations/:id} : Updates an existing formation.
     *
     * @param id the id of the formation to save.
     * @param formation the formation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formation,
     * or with status {@code 400 (Bad Request)} if the formation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Formation formation
    ) throws URISyntaxException {
        log.debug("REST request to update Formation : {}, {}", id, formation);
        if (formation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        formation = formationService.update(formation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formation.getId().toString()))
            .body(formation);
    }

    /**
     * {@code PATCH  /formations/:id} : Partial updates given fields of an existing formation, field will ignore if it is null
     *
     * @param id the id of the formation to save.
     * @param formation the formation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formation,
     * or with status {@code 400 (Bad Request)} if the formation is not valid,
     * or with status {@code 404 (Not Found)} if the formation is not found,
     * or with status {@code 500 (Internal Server Error)} if the formation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Formation> partialUpdateFormation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Formation formation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Formation partially : {}, {}", id, formation);
        if (formation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Formation> result = formationService.partialUpdate(formation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formation.getId().toString())
        );
    }

    /**
     * {@code GET  /formations} : get all the formations.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Formation>> getAllFormations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("priseencharge-is-null".equals(filter)) {
            log.debug("REST request to get all Formations where priseEnCharge is null");
            return new ResponseEntity<>(formationService.findAllWherePriseEnChargeIsNull(), HttpStatus.OK);
        }

        if ("formationinitiale-is-null".equals(filter)) {
            log.debug("REST request to get all Formations where formationInitiale is null");
            return new ResponseEntity<>(formationService.findAllWhereFormationInitialeIsNull(), HttpStatus.OK);
        }

        if ("formationcontinue-is-null".equals(filter)) {
            log.debug("REST request to get all Formations where formationContinue is null");
            return new ResponseEntity<>(formationService.findAllWhereFormationContinueIsNull(), HttpStatus.OK);
        }

        if ("concours-is-null".equals(filter)) {
            log.debug("REST request to get all Formations where concours is null");
            return new ResponseEntity<>(formationService.findAllWhereConcoursIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Formations");
        Page<Formation> page;
        if (eagerload) {
            page = formationService.findAllWithEagerRelationships(pageable);
        } else {
            page = formationService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /formations/:id} : get the "id" formation.
     *
     * @param id the id of the formation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormation(@PathVariable("id") Long id) {
        log.debug("REST request to get Formation : {}", id);
        Optional<Formation> formation = formationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formation);
    }

    /**
     * {@code DELETE  /formations/:id} : delete the "id" formation.
     *
     * @param id the id of the formation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable("id") Long id) {
        log.debug("REST request to delete Formation : {}", id);
        formationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
