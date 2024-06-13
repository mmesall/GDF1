package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FormationInitiale;
import com.mycompany.myapp.repository.FormationInitialeRepository;
import com.mycompany.myapp.service.FormationInitialeService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FormationInitiale}.
 */
@RestController
@RequestMapping("/api/formation-initiales")
public class FormationInitialeResource {

    private final Logger log = LoggerFactory.getLogger(FormationInitialeResource.class);

    private static final String ENTITY_NAME = "formationInitiale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormationInitialeService formationInitialeService;

    private final FormationInitialeRepository formationInitialeRepository;

    public FormationInitialeResource(
        FormationInitialeService formationInitialeService,
        FormationInitialeRepository formationInitialeRepository
    ) {
        this.formationInitialeService = formationInitialeService;
        this.formationInitialeRepository = formationInitialeRepository;
    }

    /**
     * {@code POST  /formation-initiales} : Create a new formationInitiale.
     *
     * @param formationInitiale the formationInitiale to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formationInitiale, or with status {@code 400 (Bad Request)} if the formationInitiale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FormationInitiale> createFormationInitiale(@RequestBody FormationInitiale formationInitiale)
        throws URISyntaxException {
        log.debug("REST request to save FormationInitiale : {}", formationInitiale);
        if (formationInitiale.getId() != null) {
            throw new BadRequestAlertException("A new formationInitiale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        formationInitiale = formationInitialeService.save(formationInitiale);
        return ResponseEntity.created(new URI("/api/formation-initiales/" + formationInitiale.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, formationInitiale.getId().toString()))
            .body(formationInitiale);
    }

    /**
     * {@code PUT  /formation-initiales/:id} : Updates an existing formationInitiale.
     *
     * @param id the id of the formationInitiale to save.
     * @param formationInitiale the formationInitiale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formationInitiale,
     * or with status {@code 400 (Bad Request)} if the formationInitiale is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formationInitiale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FormationInitiale> updateFormationInitiale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormationInitiale formationInitiale
    ) throws URISyntaxException {
        log.debug("REST request to update FormationInitiale : {}, {}", id, formationInitiale);
        if (formationInitiale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formationInitiale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formationInitialeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        formationInitiale = formationInitialeService.update(formationInitiale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formationInitiale.getId().toString()))
            .body(formationInitiale);
    }

    /**
     * {@code PATCH  /formation-initiales/:id} : Partial updates given fields of an existing formationInitiale, field will ignore if it is null
     *
     * @param id the id of the formationInitiale to save.
     * @param formationInitiale the formationInitiale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formationInitiale,
     * or with status {@code 400 (Bad Request)} if the formationInitiale is not valid,
     * or with status {@code 404 (Not Found)} if the formationInitiale is not found,
     * or with status {@code 500 (Internal Server Error)} if the formationInitiale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FormationInitiale> partialUpdateFormationInitiale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormationInitiale formationInitiale
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormationInitiale partially : {}, {}", id, formationInitiale);
        if (formationInitiale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formationInitiale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formationInitialeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormationInitiale> result = formationInitialeService.partialUpdate(formationInitiale);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formationInitiale.getId().toString())
        );
    }

    /**
     * {@code GET  /formation-initiales} : get all the formationInitiales.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formationInitiales in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FormationInitiale>> getAllFormationInitiales(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FormationInitiales");
        Page<FormationInitiale> page = formationInitialeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /formation-initiales/:id} : get the "id" formationInitiale.
     *
     * @param id the id of the formationInitiale to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formationInitiale, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FormationInitiale> getFormationInitiale(@PathVariable("id") Long id) {
        log.debug("REST request to get FormationInitiale : {}", id);
        Optional<FormationInitiale> formationInitiale = formationInitialeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formationInitiale);
    }

    /**
     * {@code DELETE  /formation-initiales/:id} : delete the "id" formationInitiale.
     *
     * @param id the id of the formationInitiale to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormationInitiale(@PathVariable("id") Long id) {
        log.debug("REST request to delete FormationInitiale : {}", id);
        formationInitialeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
