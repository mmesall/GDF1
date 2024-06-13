package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ServiceMFPAI;
import com.mycompany.myapp.repository.ServiceMFPAIRepository;
import com.mycompany.myapp.service.ServiceMFPAIService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ServiceMFPAI}.
 */
@RestController
@RequestMapping("/api/service-mfpais")
public class ServiceMFPAIResource {

    private final Logger log = LoggerFactory.getLogger(ServiceMFPAIResource.class);

    private static final String ENTITY_NAME = "serviceMFPAI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceMFPAIService serviceMFPAIService;

    private final ServiceMFPAIRepository serviceMFPAIRepository;

    public ServiceMFPAIResource(ServiceMFPAIService serviceMFPAIService, ServiceMFPAIRepository serviceMFPAIRepository) {
        this.serviceMFPAIService = serviceMFPAIService;
        this.serviceMFPAIRepository = serviceMFPAIRepository;
    }

    /**
     * {@code POST  /service-mfpais} : Create a new serviceMFPAI.
     *
     * @param serviceMFPAI the serviceMFPAI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceMFPAI, or with status {@code 400 (Bad Request)} if the serviceMFPAI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceMFPAI> createServiceMFPAI(@Valid @RequestBody ServiceMFPAI serviceMFPAI) throws URISyntaxException {
        log.debug("REST request to save ServiceMFPAI : {}", serviceMFPAI);
        if (serviceMFPAI.getId() != null) {
            throw new BadRequestAlertException("A new serviceMFPAI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceMFPAI = serviceMFPAIService.save(serviceMFPAI);
        return ResponseEntity.created(new URI("/api/service-mfpais/" + serviceMFPAI.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, serviceMFPAI.getId().toString()))
            .body(serviceMFPAI);
    }

    /**
     * {@code PUT  /service-mfpais/:id} : Updates an existing serviceMFPAI.
     *
     * @param id the id of the serviceMFPAI to save.
     * @param serviceMFPAI the serviceMFPAI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceMFPAI,
     * or with status {@code 400 (Bad Request)} if the serviceMFPAI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceMFPAI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceMFPAI> updateServiceMFPAI(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceMFPAI serviceMFPAI
    ) throws URISyntaxException {
        log.debug("REST request to update ServiceMFPAI : {}, {}", id, serviceMFPAI);
        if (serviceMFPAI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceMFPAI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceMFPAIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceMFPAI = serviceMFPAIService.update(serviceMFPAI);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceMFPAI.getId().toString()))
            .body(serviceMFPAI);
    }

    /**
     * {@code PATCH  /service-mfpais/:id} : Partial updates given fields of an existing serviceMFPAI, field will ignore if it is null
     *
     * @param id the id of the serviceMFPAI to save.
     * @param serviceMFPAI the serviceMFPAI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceMFPAI,
     * or with status {@code 400 (Bad Request)} if the serviceMFPAI is not valid,
     * or with status {@code 404 (Not Found)} if the serviceMFPAI is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceMFPAI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceMFPAI> partialUpdateServiceMFPAI(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceMFPAI serviceMFPAI
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServiceMFPAI partially : {}, {}", id, serviceMFPAI);
        if (serviceMFPAI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceMFPAI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceMFPAIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceMFPAI> result = serviceMFPAIService.partialUpdate(serviceMFPAI);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceMFPAI.getId().toString())
        );
    }

    /**
     * {@code GET  /service-mfpais} : get all the serviceMFPAIS.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceMFPAIS in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceMFPAI>> getAllServiceMFPAIS(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("agent-is-null".equals(filter)) {
            log.debug("REST request to get all ServiceMFPAIs where agent is null");
            return new ResponseEntity<>(serviceMFPAIService.findAllWhereAgentIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of ServiceMFPAIS");
        Page<ServiceMFPAI> page = serviceMFPAIService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-mfpais/:id} : get the "id" serviceMFPAI.
     *
     * @param id the id of the serviceMFPAI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceMFPAI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceMFPAI> getServiceMFPAI(@PathVariable("id") Long id) {
        log.debug("REST request to get ServiceMFPAI : {}", id);
        Optional<ServiceMFPAI> serviceMFPAI = serviceMFPAIService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceMFPAI);
    }

    /**
     * {@code DELETE  /service-mfpais/:id} : delete the "id" serviceMFPAI.
     *
     * @param id the id of the serviceMFPAI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceMFPAI(@PathVariable("id") Long id) {
        log.debug("REST request to delete ServiceMFPAI : {}", id);
        serviceMFPAIService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
