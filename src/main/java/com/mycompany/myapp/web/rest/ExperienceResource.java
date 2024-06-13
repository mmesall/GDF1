package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Experience;
import com.mycompany.myapp.repository.ExperienceRepository;
import com.mycompany.myapp.service.ExperienceService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Experience}.
 */
@RestController
@RequestMapping("/api/experiences")
public class ExperienceResource {

    private final Logger log = LoggerFactory.getLogger(ExperienceResource.class);

    private static final String ENTITY_NAME = "experience";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExperienceService experienceService;

    private final ExperienceRepository experienceRepository;

    public ExperienceResource(ExperienceService experienceService, ExperienceRepository experienceRepository) {
        this.experienceService = experienceService;
        this.experienceRepository = experienceRepository;
    }

    /**
     * {@code POST  /experiences} : Create a new experience.
     *
     * @param experience the experience to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new experience, or with status {@code 400 (Bad Request)} if the experience has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Experience> createExperience(@Valid @RequestBody Experience experience) throws URISyntaxException {
        log.debug("REST request to save Experience : {}", experience);
        if (experience.getId() != null) {
            throw new BadRequestAlertException("A new experience cannot already have an ID", ENTITY_NAME, "idexists");
        }
        experience = experienceService.save(experience);
        return ResponseEntity.created(new URI("/api/experiences/" + experience.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, experience.getId().toString()))
            .body(experience);
    }

    /**
     * {@code PUT  /experiences/:id} : Updates an existing experience.
     *
     * @param id the id of the experience to save.
     * @param experience the experience to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated experience,
     * or with status {@code 400 (Bad Request)} if the experience is not valid,
     * or with status {@code 500 (Internal Server Error)} if the experience couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Experience> updateExperience(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Experience experience
    ) throws URISyntaxException {
        log.debug("REST request to update Experience : {}, {}", id, experience);
        if (experience.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, experience.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!experienceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        experience = experienceService.update(experience);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, experience.getId().toString()))
            .body(experience);
    }

    /**
     * {@code PATCH  /experiences/:id} : Partial updates given fields of an existing experience, field will ignore if it is null
     *
     * @param id the id of the experience to save.
     * @param experience the experience to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated experience,
     * or with status {@code 400 (Bad Request)} if the experience is not valid,
     * or with status {@code 404 (Not Found)} if the experience is not found,
     * or with status {@code 500 (Internal Server Error)} if the experience couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Experience> partialUpdateExperience(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Experience experience
    ) throws URISyntaxException {
        log.debug("REST request to partial update Experience partially : {}, {}", id, experience);
        if (experience.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, experience.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!experienceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Experience> result = experienceService.partialUpdate(experience);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, experience.getId().toString())
        );
    }

    /**
     * {@code GET  /experiences} : get all the experiences.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of experiences in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Experience>> getAllExperiences(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Experiences");
        Page<Experience> page;
        if (eagerload) {
            page = experienceService.findAllWithEagerRelationships(pageable);
        } else {
            page = experienceService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /experiences/:id} : get the "id" experience.
     *
     * @param id the id of the experience to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the experience, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Experience> getExperience(@PathVariable("id") Long id) {
        log.debug("REST request to get Experience : {}", id);
        Optional<Experience> experience = experienceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(experience);
    }

    /**
     * {@code DELETE  /experiences/:id} : delete the "id" experience.
     *
     * @param id the id of the experience to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable("id") Long id) {
        log.debug("REST request to delete Experience : {}", id);
        experienceService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
