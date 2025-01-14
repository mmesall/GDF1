package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Eleve;
import com.mycompany.myapp.repository.EleveRepository;
import com.mycompany.myapp.service.EleveService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Eleve}.
 */
@RestController
@RequestMapping("/api/eleves")
public class EleveResource {

    private final Logger log = LoggerFactory.getLogger(EleveResource.class);

    private static final String ENTITY_NAME = "eleve";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EleveService eleveService;

    private final EleveRepository eleveRepository;

    public EleveResource(EleveService eleveService, EleveRepository eleveRepository) {
        this.eleveService = eleveService;
        this.eleveRepository = eleveRepository;
    }

    /**
     * {@code POST  /eleves} : Create a new eleve.
     *
     * @param eleve the eleve to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eleve, or with status {@code 400 (Bad Request)} if the eleve has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eleve> createEleve(@Valid @RequestBody Eleve eleve) throws URISyntaxException {
        log.debug("REST request to save Eleve : {}", eleve);
        if (eleve.getId() != null) {
            throw new BadRequestAlertException("A new eleve cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eleve = eleveService.save(eleve);
        return ResponseEntity.created(new URI("/api/eleves/" + eleve.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, eleve.getId().toString()))
            .body(eleve);
    }

    /**
     * {@code PUT  /eleves/:id} : Updates an existing eleve.
     *
     * @param id the id of the eleve to save.
     * @param eleve the eleve to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eleve,
     * or with status {@code 400 (Bad Request)} if the eleve is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eleve couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Eleve> updateEleve(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Eleve eleve)
        throws URISyntaxException {
        log.debug("REST request to update Eleve : {}, {}", id, eleve);
        if (eleve.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eleve.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eleveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        eleve = eleveService.update(eleve);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eleve.getId().toString()))
            .body(eleve);
    }

    /**
     * {@code PATCH  /eleves/:id} : Partial updates given fields of an existing eleve, field will ignore if it is null
     *
     * @param id the id of the eleve to save.
     * @param eleve the eleve to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eleve,
     * or with status {@code 400 (Bad Request)} if the eleve is not valid,
     * or with status {@code 404 (Not Found)} if the eleve is not found,
     * or with status {@code 500 (Internal Server Error)} if the eleve couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Eleve> partialUpdateEleve(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Eleve eleve
    ) throws URISyntaxException {
        log.debug("REST request to partial update Eleve partially : {}, {}", id, eleve);
        if (eleve.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eleve.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eleveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Eleve> result = eleveService.partialUpdate(eleve);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eleve.getId().toString())
        );
    }

    /**
     * {@code GET  /eleves} : get all the eleves.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eleves in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Eleve>> getAllEleves(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("dossier-is-null".equals(filter)) {
            log.debug("REST request to get all Eleves where dossier is null");
            return new ResponseEntity<>(eleveService.findAllWhereDossierIsNull(), HttpStatus.OK);
        }

        if ("demandeur-is-null".equals(filter)) {
            log.debug("REST request to get all Eleves where demandeur is null");
            return new ResponseEntity<>(eleveService.findAllWhereDemandeurIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Eleves");
        Page<Eleve> page;
        if (eagerload) {
            page = eleveService.findAllWithEagerRelationships(pageable);
        } else {
            page = eleveService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eleves/:id} : get the "id" eleve.
     *
     * @param id the id of the eleve to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eleve, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eleve> getEleve(@PathVariable("id") Long id) {
        log.debug("REST request to get Eleve : {}", id);
        Optional<Eleve> eleve = eleveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eleve);
    }

    /**
     * {@code DELETE  /eleves/:id} : delete the "id" eleve.
     *
     * @param id the id of the eleve to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEleve(@PathVariable("id") Long id) {
        log.debug("REST request to delete Eleve : {}", id);
        eleveService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
