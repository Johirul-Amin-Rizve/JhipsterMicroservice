package com.ailaaj.paf.web.rest;

import com.ailaaj.paf.domain.Demand;
import com.ailaaj.paf.repository.DemandRepository;
import com.ailaaj.paf.service.DemandService;
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
 * REST controller for managing {@link com.ailaaj.paf.domain.Demand}.
 */
@RestController
@RequestMapping("/api")
public class DemandResource {

    private final Logger log = LoggerFactory.getLogger(DemandResource.class);

    private static final String ENTITY_NAME = "dataProcessorDemand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandService demandService;

    private final DemandRepository demandRepository;

    public DemandResource(DemandService demandService, DemandRepository demandRepository) {
        this.demandService = demandService;
        this.demandRepository = demandRepository;
    }

    /**
     * {@code POST  /demands} : Create a new demand.
     *
     * @param demand the demand to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demand, or with status {@code 400 (Bad Request)} if the demand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demands")
    public ResponseEntity<Demand> createDemand(@RequestBody Demand demand) throws URISyntaxException {
        log.debug("REST request to save Demand : {}", demand);
        if (demand.getId() != null) {
            throw new BadRequestAlertException("A new demand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Demand result = demandService.save(demand);
        return ResponseEntity
            .created(new URI("/api/demands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demands/:id} : Updates an existing demand.
     *
     * @param id the id of the demand to save.
     * @param demand the demand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demand,
     * or with status {@code 400 (Bad Request)} if the demand is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demands/{id}")
    public ResponseEntity<Demand> updateDemand(@PathVariable(value = "id", required = false) final Long id, @RequestBody Demand demand)
        throws URISyntaxException {
        log.debug("REST request to update Demand : {}, {}", id, demand);
        if (demand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Demand result = demandService.update(demand);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demand.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demands/:id} : Partial updates given fields of an existing demand, field will ignore if it is null
     *
     * @param id the id of the demand to save.
     * @param demand the demand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demand,
     * or with status {@code 400 (Bad Request)} if the demand is not valid,
     * or with status {@code 404 (Not Found)} if the demand is not found,
     * or with status {@code 500 (Internal Server Error)} if the demand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demands/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Demand> partialUpdateDemand(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Demand demand
    ) throws URISyntaxException {
        log.debug("REST request to partial update Demand partially : {}, {}", id, demand);
        if (demand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Demand> result = demandService.partialUpdate(demand);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demand.getId().toString())
        );
    }

    /**
     * {@code GET  /demands} : get all the demands.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demands in body.
     */
    @GetMapping("/demands")
    public ResponseEntity<List<Demand>> getAllDemands(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Demands");
        Page<Demand> page = demandService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demands/:id} : get the "id" demand.
     *
     * @param id the id of the demand to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demand, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demands/{id}")
    public ResponseEntity<Demand> getDemand(@PathVariable Long id) {
        log.debug("REST request to get Demand : {}", id);
        Optional<Demand> demand = demandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demand);
    }

    /**
     * {@code DELETE  /demands/:id} : delete the "id" demand.
     *
     * @param id the id of the demand to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demands/{id}")
    public ResponseEntity<Void> deleteDemand(@PathVariable Long id) {
        log.debug("REST request to delete Demand : {}", id);
        demandService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
