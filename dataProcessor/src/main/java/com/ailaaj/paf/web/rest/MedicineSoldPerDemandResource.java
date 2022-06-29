package com.ailaaj.paf.web.rest;

import com.ailaaj.paf.domain.MedicineSoldPerDemand;
import com.ailaaj.paf.repository.MedicineSoldPerDemandRepository;
import com.ailaaj.paf.service.MedicineSoldPerDemandService;
import com.ailaaj.paf.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.ailaaj.paf.domain.MedicineSoldPerDemand}.
 */
@RestController
@RequestMapping("/api")
public class MedicineSoldPerDemandResource {

    private final Logger log = LoggerFactory.getLogger(MedicineSoldPerDemandResource.class);

    private static final String ENTITY_NAME = "dataProcessorMedicineSoldPerDemand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicineSoldPerDemandService medicineSoldPerDemandService;

    private final MedicineSoldPerDemandRepository medicineSoldPerDemandRepository;

    public MedicineSoldPerDemandResource(
        MedicineSoldPerDemandService medicineSoldPerDemandService,
        MedicineSoldPerDemandRepository medicineSoldPerDemandRepository
    ) {
        this.medicineSoldPerDemandService = medicineSoldPerDemandService;
        this.medicineSoldPerDemandRepository = medicineSoldPerDemandRepository;
    }

    /**
     * {@code POST  /medicine-sold-per-demands} : Create a new medicineSoldPerDemand.
     *
     * @param medicineSoldPerDemand the medicineSoldPerDemand to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicineSoldPerDemand, or with status {@code 400 (Bad Request)} if the medicineSoldPerDemand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medicine-sold-per-demands")
    public ResponseEntity<MedicineSoldPerDemand> createMedicineSoldPerDemand(@RequestBody MedicineSoldPerDemand medicineSoldPerDemand)
        throws URISyntaxException {
        log.debug("REST request to save MedicineSoldPerDemand : {}", medicineSoldPerDemand);
        if (medicineSoldPerDemand.getId() != null) {
            throw new BadRequestAlertException("A new medicineSoldPerDemand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicineSoldPerDemand result = medicineSoldPerDemandService.save(medicineSoldPerDemand);
        return ResponseEntity
            .created(new URI("/api/medicine-sold-per-demands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medicine-sold-per-demands/:id} : Updates an existing medicineSoldPerDemand.
     *
     * @param id the id of the medicineSoldPerDemand to save.
     * @param medicineSoldPerDemand the medicineSoldPerDemand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicineSoldPerDemand,
     * or with status {@code 400 (Bad Request)} if the medicineSoldPerDemand is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicineSoldPerDemand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medicine-sold-per-demands/{id}")
    public ResponseEntity<MedicineSoldPerDemand> updateMedicineSoldPerDemand(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicineSoldPerDemand medicineSoldPerDemand
    ) throws URISyntaxException {
        log.debug("REST request to update MedicineSoldPerDemand : {}, {}", id, medicineSoldPerDemand);
        if (medicineSoldPerDemand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicineSoldPerDemand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicineSoldPerDemandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MedicineSoldPerDemand result = medicineSoldPerDemandService.update(medicineSoldPerDemand);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicineSoldPerDemand.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /medicine-sold-per-demands/:id} : Partial updates given fields of an existing medicineSoldPerDemand, field will ignore if it is null
     *
     * @param id the id of the medicineSoldPerDemand to save.
     * @param medicineSoldPerDemand the medicineSoldPerDemand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicineSoldPerDemand,
     * or with status {@code 400 (Bad Request)} if the medicineSoldPerDemand is not valid,
     * or with status {@code 404 (Not Found)} if the medicineSoldPerDemand is not found,
     * or with status {@code 500 (Internal Server Error)} if the medicineSoldPerDemand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/medicine-sold-per-demands/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MedicineSoldPerDemand> partialUpdateMedicineSoldPerDemand(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicineSoldPerDemand medicineSoldPerDemand
    ) throws URISyntaxException {
        log.debug("REST request to partial update MedicineSoldPerDemand partially : {}, {}", id, medicineSoldPerDemand);
        if (medicineSoldPerDemand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicineSoldPerDemand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicineSoldPerDemandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MedicineSoldPerDemand> result = medicineSoldPerDemandService.partialUpdate(medicineSoldPerDemand);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicineSoldPerDemand.getId().toString())
        );
    }

    /**
     * {@code GET  /medicine-sold-per-demands} : get all the medicineSoldPerDemands.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicineSoldPerDemands in body.
     */
    @GetMapping("/medicine-sold-per-demands")
    public ResponseEntity<List<MedicineSoldPerDemand>> getAllMedicineSoldPerDemands(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MedicineSoldPerDemands");
        Page<MedicineSoldPerDemand> page = medicineSoldPerDemandService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medicine-sold-per-demands/:id} : get the "id" medicineSoldPerDemand.
     *
     * @param id the id of the medicineSoldPerDemand to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicineSoldPerDemand, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medicine-sold-per-demands/{id}")
    public ResponseEntity<MedicineSoldPerDemand> getMedicineSoldPerDemand(@PathVariable Long id) {
        log.debug("REST request to get MedicineSoldPerDemand : {}", id);
        Optional<MedicineSoldPerDemand> medicineSoldPerDemand = medicineSoldPerDemandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicineSoldPerDemand);
    }

    /**
     * {@code DELETE  /medicine-sold-per-demands/:id} : delete the "id" medicineSoldPerDemand.
     *
     * @param id the id of the medicineSoldPerDemand to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medicine-sold-per-demands/{id}")
    public ResponseEntity<Void> deleteMedicineSoldPerDemand(@PathVariable Long id) {
        log.debug("REST request to delete MedicineSoldPerDemand : {}", id);
        medicineSoldPerDemandService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
