package com.ailaaj.paf.web.rest;

import com.ailaaj.paf.domain.DemanInfo;
import com.ailaaj.paf.repository.DemanInfoRepository;
import com.ailaaj.paf.service.DemanInfoService;
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
 * REST controller for managing {@link com.ailaaj.paf.domain.DemanInfo}.
 */
@RestController
@RequestMapping("/api")
public class DemanInfoResource {

    private final Logger log = LoggerFactory.getLogger(DemanInfoResource.class);

    private static final String ENTITY_NAME = "orderPlacerDemanInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemanInfoService demanInfoService;

    private final DemanInfoRepository demanInfoRepository;

    public DemanInfoResource(DemanInfoService demanInfoService, DemanInfoRepository demanInfoRepository) {
        this.demanInfoService = demanInfoService;
        this.demanInfoRepository = demanInfoRepository;
    }

    /**
     * {@code POST  /deman-infos} : Create a new demanInfo.
     *
     * @param demanInfo the demanInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demanInfo, or with status {@code 400 (Bad Request)} if the demanInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deman-infos")
    public ResponseEntity<DemanInfo> createDemanInfo(@RequestBody DemanInfo demanInfo) throws URISyntaxException {
        log.debug("REST request to save DemanInfo : {}", demanInfo);
        if (demanInfo.getId() != null) {
            throw new BadRequestAlertException("A new demanInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemanInfo result = demanInfoService.save(demanInfo);
        return ResponseEntity
            .created(new URI("/api/deman-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deman-infos/:id} : Updates an existing demanInfo.
     *
     * @param id the id of the demanInfo to save.
     * @param demanInfo the demanInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demanInfo,
     * or with status {@code 400 (Bad Request)} if the demanInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demanInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deman-infos/{id}")
    public ResponseEntity<DemanInfo> updateDemanInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemanInfo demanInfo
    ) throws URISyntaxException {
        log.debug("REST request to update DemanInfo : {}, {}", id, demanInfo);
        if (demanInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demanInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demanInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemanInfo result = demanInfoService.update(demanInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demanInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deman-infos/:id} : Partial updates given fields of an existing demanInfo, field will ignore if it is null
     *
     * @param id the id of the demanInfo to save.
     * @param demanInfo the demanInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demanInfo,
     * or with status {@code 400 (Bad Request)} if the demanInfo is not valid,
     * or with status {@code 404 (Not Found)} if the demanInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the demanInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deman-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemanInfo> partialUpdateDemanInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemanInfo demanInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemanInfo partially : {}, {}", id, demanInfo);
        if (demanInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demanInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demanInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemanInfo> result = demanInfoService.partialUpdate(demanInfo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demanInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /deman-infos} : get all the demanInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demanInfos in body.
     */
    @GetMapping("/deman-infos")
    public ResponseEntity<List<DemanInfo>> getAllDemanInfos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DemanInfos");
        Page<DemanInfo> page = demanInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deman-infos/:id} : get the "id" demanInfo.
     *
     * @param id the id of the demanInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demanInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deman-infos/{id}")
    public ResponseEntity<DemanInfo> getDemanInfo(@PathVariable Long id) {
        log.debug("REST request to get DemanInfo : {}", id);
        Optional<DemanInfo> demanInfo = demanInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demanInfo);
    }

    /**
     * {@code DELETE  /deman-infos/:id} : delete the "id" demanInfo.
     *
     * @param id the id of the demanInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deman-infos/{id}")
    public ResponseEntity<Void> deleteDemanInfo(@PathVariable Long id) {
        log.debug("REST request to delete DemanInfo : {}", id);
        demanInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
