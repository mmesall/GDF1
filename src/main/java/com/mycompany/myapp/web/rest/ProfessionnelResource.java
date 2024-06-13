package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Professionnel;
import com.mycompany.myapp.repository.ProfessionnelRepository;
import com.mycompany.myapp.service.ProfessionnelService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Professionnel}.
 */
@RestController
@RequestMapping("/api/professionnels")
public class ProfessionnelResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionnelResource.class);

    private static final String ENTITY_NAME = "professionnel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfessionnelService professionnelService;

    private final ProfessionnelRepository professionnelRepository;

    public ProfessionnelResource(ProfessionnelService professionnelService, ProfessionnelRepository professionnelRepository) {
        this.professionnelService = professionnelService;
        this.professionnelRepository = professionnelRepository;
    }

    /**
     * {@code POST  /professionnels} : Create a new professionnel.
     *
     * @param professionnel the professionnel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new professionnel, or with status {@code 400 (Bad Request)} if the professionnel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Professionnel> createProfessionnel(@Valid @RequestBody Professionnel professionnel) throws URISyntaxException {
        log.debug("REST request to save Professionnel : {}", professionnel);
        if (professionnel.getId() != null) {
            throw new BadRequestAlertException("A new professionnel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        professionnel = professionnelService.save(professionnel);
        return ResponseEntity.created(new URI("/api/professionnels/" + professionnel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, professionnel.getId().toString()))
            .body(professionnel);
    }

    /**
     * {@code PUT  /professionnels/:id} : Updates an existing professionnel.
     *
     * @param id the id of the professionnel to save.
     * @param professionnel the professionnel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professionnel,
     * or with status {@code 400 (Bad Request)} if the professionnel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the professionnel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Professionnel> updateProfessionnel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Professionnel professionnel
    ) throws URISyntaxException {
        log.debug("REST request to update Professionnel : {}, {}", id, professionnel);
        if (professionnel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, professionnel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!professionnelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        professionnel = professionnelService.update(professionnel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professionnel.getId().toString()))
            .body(professionnel);
    }

    /**
     * {@code PATCH  /professionnels/:id} : Partial updates given fields of an existing professionnel, field will ignore if it is null
     *
     * @param id the id of the professionnel to save.
     * @param professionnel the professionnel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professionnel,
     * or with status {@code 400 (Bad Request)} if the professionnel is not valid,
     * or with status {@code 404 (Not Found)} if the professionnel is not found,
     * or with status {@code 500 (Internal Server Error)} if the professionnel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Professionnel> partialUpdateProfessionnel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Professionnel professionnel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Professionnel partially : {}, {}", id, professionnel);
        if (professionnel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, professionnel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!professionnelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Professionnel> result = professionnelService.partialUpdate(professionnel);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professionnel.getId().toString())
        );
    }

    /**
     * {@code GET  /professionnels} : get all the professionnels.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of professionnels in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Professionnel>> getAllProfessionnels(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("dossier-is-null".equals(filter)) {
            log.debug("REST request to get all Professionnels where dossier is null");
            return new ResponseEntity<>(professionnelService.findAllWhereDossierIsNull(), HttpStatus.OK);
        }

        if ("demandeur-is-null".equals(filter)) {
            log.debug("REST request to get all Professionnels where demandeur is null");
            return new ResponseEntity<>(professionnelService.findAllWhereDemandeurIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Professionnels");
        Page<Professionnel> page;
        if (eagerload) {
            page = professionnelService.findAllWithEagerRelationships(pageable);
        } else {
            page = professionnelService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /professionnels/:id} : get the "id" professionnel.
     *
     * @param id the id of the professionnel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the professionnel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Professionnel> getProfessionnel(@PathVariable("id") Long id) {
        log.debug("REST request to get Professionnel : {}", id);
        Optional<Professionnel> professionnel = professionnelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(professionnel);
    }

    /**
     * {@code DELETE  /professionnels/:id} : delete the "id" professionnel.
     *
     * @param id the id of the professionnel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessionnel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Professionnel : {}", id);
        professionnelService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
