package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FormationContinue;
import com.mycompany.myapp.repository.FormationContinueRepository;
import com.mycompany.myapp.service.FormationContinueService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FormationContinue}.
 */
@RestController
@RequestMapping("/api/formation-continues")
public class FormationContinueResource {

    private final Logger log = LoggerFactory.getLogger(FormationContinueResource.class);

    private static final String ENTITY_NAME = "formationContinue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormationContinueService formationContinueService;

    private final FormationContinueRepository formationContinueRepository;

    public FormationContinueResource(
        FormationContinueService formationContinueService,
        FormationContinueRepository formationContinueRepository
    ) {
        this.formationContinueService = formationContinueService;
        this.formationContinueRepository = formationContinueRepository;
    }

    /**
     * {@code POST  /formation-continues} : Create a new formationContinue.
     *
     * @param formationContinue the formationContinue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formationContinue, or with status {@code 400 (Bad Request)} if the formationContinue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FormationContinue> createFormationContinue(@RequestBody FormationContinue formationContinue)
        throws URISyntaxException {
        log.debug("REST request to save FormationContinue : {}", formationContinue);
        if (formationContinue.getId() != null) {
            throw new BadRequestAlertException("A new formationContinue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        formationContinue = formationContinueService.save(formationContinue);
        return ResponseEntity.created(new URI("/api/formation-continues/" + formationContinue.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, formationContinue.getId().toString()))
            .body(formationContinue);
    }

    /**
     * {@code PUT  /formation-continues/:id} : Updates an existing formationContinue.
     *
     * @param id the id of the formationContinue to save.
     * @param formationContinue the formationContinue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formationContinue,
     * or with status {@code 400 (Bad Request)} if the formationContinue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formationContinue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FormationContinue> updateFormationContinue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormationContinue formationContinue
    ) throws URISyntaxException {
        log.debug("REST request to update FormationContinue : {}, {}", id, formationContinue);
        if (formationContinue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formationContinue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formationContinueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        formationContinue = formationContinueService.update(formationContinue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formationContinue.getId().toString()))
            .body(formationContinue);
    }

    /**
     * {@code PATCH  /formation-continues/:id} : Partial updates given fields of an existing formationContinue, field will ignore if it is null
     *
     * @param id the id of the formationContinue to save.
     * @param formationContinue the formationContinue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formationContinue,
     * or with status {@code 400 (Bad Request)} if the formationContinue is not valid,
     * or with status {@code 404 (Not Found)} if the formationContinue is not found,
     * or with status {@code 500 (Internal Server Error)} if the formationContinue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FormationContinue> partialUpdateFormationContinue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormationContinue formationContinue
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormationContinue partially : {}, {}", id, formationContinue);
        if (formationContinue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formationContinue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formationContinueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormationContinue> result = formationContinueService.partialUpdate(formationContinue);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formationContinue.getId().toString())
        );
    }

    /**
     * {@code GET  /formation-continues} : get all the formationContinues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formationContinues in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FormationContinue>> getAllFormationContinues(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FormationContinues");
        Page<FormationContinue> page = formationContinueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /formation-continues/:id} : get the "id" formationContinue.
     *
     * @param id the id of the formationContinue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formationContinue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FormationContinue> getFormationContinue(@PathVariable("id") Long id) {
        log.debug("REST request to get FormationContinue : {}", id);
        Optional<FormationContinue> formationContinue = formationContinueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formationContinue);
    }

    /**
     * {@code DELETE  /formation-continues/:id} : delete the "id" formationContinue.
     *
     * @param id the id of the formationContinue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormationContinue(@PathVariable("id") Long id) {
        log.debug("REST request to delete FormationContinue : {}", id);
        formationContinueService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
