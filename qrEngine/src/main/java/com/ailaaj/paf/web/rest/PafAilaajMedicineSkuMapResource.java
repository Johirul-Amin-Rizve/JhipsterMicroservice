package com.ailaaj.paf.web.rest;

import com.ailaaj.paf.config.KafkaOrderConsumer;
import com.ailaaj.paf.config.KafkaOrderProducer;
import com.ailaaj.paf.domain.PafAilaajMedicineSkuMap;
import com.ailaaj.paf.repository.PafAilaajMedicineSkuMapRepository;
import com.ailaaj.paf.service.PafAilaajMedicineSkuMapService;
import com.ailaaj.paf.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ailaaj.paf.domain.PafAilaajMedicineSkuMap}.
 */
@RestController
@RequestMapping("/api")
public class PafAilaajMedicineSkuMapResource {

    private final Logger log = LoggerFactory.getLogger(PafAilaajMedicineSkuMapResource.class);

    private static final String ENTITY_NAME = "qrEnginePafAilaajMedicineSkuMap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PafAilaajMedicineSkuMapService pafAilaajMedicineSkuMapService;

    private final PafAilaajMedicineSkuMapRepository pafAilaajMedicineSkuMapRepository;

    private final MessageChannel output;

    public PafAilaajMedicineSkuMapResource(
        @Qualifier(KafkaOrderProducer.CHANNELNAME) MessageChannel output,
        PafAilaajMedicineSkuMapService pafAilaajMedicineSkuMapService,
        PafAilaajMedicineSkuMapRepository pafAilaajMedicineSkuMapRepository
    ) {
        this.output = output;
        this.pafAilaajMedicineSkuMapService = pafAilaajMedicineSkuMapService;
        this.pafAilaajMedicineSkuMapRepository = pafAilaajMedicineSkuMapRepository;
    }

    /**
     * {@code POST  /paf-ailaaj-medicine-sku-maps} : Create a new pafAilaajMedicineSkuMap.
     *
     * @param pafAilaajMedicineSkuMap the pafAilaajMedicineSkuMap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pafAilaajMedicineSkuMap, or with status {@code 400 (Bad Request)} if the pafAilaajMedicineSkuMap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paf-ailaaj-medicine-sku-maps")
    public ResponseEntity<PafAilaajMedicineSkuMap> createPafAilaajMedicineSkuMap(
        @RequestBody PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap
    ) throws URISyntaxException {
        log.debug("REST request to save PafAilaajMedicineSkuMap : {}", pafAilaajMedicineSkuMap);
        if (pafAilaajMedicineSkuMap.getId() != null) {
            throw new BadRequestAlertException("A new pafAilaajMedicineSkuMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PafAilaajMedicineSkuMap result = pafAilaajMedicineSkuMapService.save(pafAilaajMedicineSkuMap);
        return ResponseEntity
            .created(new URI("/api/paf-ailaaj-medicine-sku-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paf-ailaaj-medicine-sku-maps/:id} : Updates an existing pafAilaajMedicineSkuMap.
     *
     * @param id the id of the pafAilaajMedicineSkuMap to save.
     * @param pafAilaajMedicineSkuMap the pafAilaajMedicineSkuMap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pafAilaajMedicineSkuMap,
     * or with status {@code 400 (Bad Request)} if the pafAilaajMedicineSkuMap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pafAilaajMedicineSkuMap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paf-ailaaj-medicine-sku-maps/{id}")
    public ResponseEntity<PafAilaajMedicineSkuMap> updatePafAilaajMedicineSkuMap(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap
    ) throws URISyntaxException {
        log.debug("REST request to update PafAilaajMedicineSkuMap : {}, {}", id, pafAilaajMedicineSkuMap);
        if (pafAilaajMedicineSkuMap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pafAilaajMedicineSkuMap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pafAilaajMedicineSkuMapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PafAilaajMedicineSkuMap result = pafAilaajMedicineSkuMapService.update(pafAilaajMedicineSkuMap);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pafAilaajMedicineSkuMap.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /paf-ailaaj-medicine-sku-maps/:id} : Partial updates given fields of an existing pafAilaajMedicineSkuMap, field will ignore if it is null
     *
     * @param id the id of the pafAilaajMedicineSkuMap to save.
     * @param pafAilaajMedicineSkuMap the pafAilaajMedicineSkuMap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pafAilaajMedicineSkuMap,
     * or with status {@code 400 (Bad Request)} if the pafAilaajMedicineSkuMap is not valid,
     * or with status {@code 404 (Not Found)} if the pafAilaajMedicineSkuMap is not found,
     * or with status {@code 500 (Internal Server Error)} if the pafAilaajMedicineSkuMap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/paf-ailaaj-medicine-sku-maps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PafAilaajMedicineSkuMap> partialUpdatePafAilaajMedicineSkuMap(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap
    ) throws URISyntaxException {
        log.debug("REST request to partial update PafAilaajMedicineSkuMap partially : {}, {}", id, pafAilaajMedicineSkuMap);
        if (pafAilaajMedicineSkuMap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pafAilaajMedicineSkuMap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pafAilaajMedicineSkuMapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PafAilaajMedicineSkuMap> result = pafAilaajMedicineSkuMapService.partialUpdate(pafAilaajMedicineSkuMap);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pafAilaajMedicineSkuMap.getId().toString())
        );
    }

    /**
     * {@code GET  /paf-ailaaj-medicine-sku-maps} : get all the pafAilaajMedicineSkuMaps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pafAilaajMedicineSkuMaps in body.
     */
    @GetMapping("/paf-ailaaj-medicine-sku-maps")
    public ResponseEntity<List<PafAilaajMedicineSkuMap>> getAllPafAilaajMedicineSkuMaps(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PafAilaajMedicineSkuMaps");
        Page<PafAilaajMedicineSkuMap> page = pafAilaajMedicineSkuMapService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /paf-ailaaj-medicine-sku-maps/:id} : get the "id" pafAilaajMedicineSkuMap.
     *
     * @param id the id of the pafAilaajMedicineSkuMap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pafAilaajMedicineSkuMap, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paf-ailaaj-medicine-sku-maps/{id}")
    public ResponseEntity<PafAilaajMedicineSkuMap> getPafAilaajMedicineSkuMap(@PathVariable Long id) {
        log.debug("REST request to get PafAilaajMedicineSkuMap : {}", id);
        Optional<PafAilaajMedicineSkuMap> pafAilaajMedicineSkuMap = pafAilaajMedicineSkuMapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pafAilaajMedicineSkuMap);
    }

    /**
     * {@code DELETE  /paf-ailaaj-medicine-sku-maps/:id} : delete the "id" pafAilaajMedicineSkuMap.
     *
     * @param id the id of the pafAilaajMedicineSkuMap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paf-ailaaj-medicine-sku-maps/{id}")
    public ResponseEntity<Void> deletePafAilaajMedicineSkuMap(@PathVariable Long id) {
        log.debug("REST request to delete PafAilaajMedicineSkuMap : {}", id);
        pafAilaajMedicineSkuMapService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/publish")
    public void publish() {
        OrderDto orderDto = new OrderDto();
        orderDto.setAilaajMedicineId(1L);
        orderDto.setMedicineId(2L);
        output.send(MessageBuilder.withPayload(orderDto).build());
    }
}
