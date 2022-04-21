package com.myapp.web.rest;

import com.myapp.domain.Teacher;
import com.myapp.repository.TeacherRepository;
import com.myapp.service.TeacherService;
import com.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.myapp.domain.Teacher}.
 */
@RestController
@RequestMapping("/api")
public class TeacherResource {

    private final Logger log = LoggerFactory.getLogger(TeacherResource.class);

    private static final String ENTITY_NAME = "teacher";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeacherService teacherService;

    private final TeacherRepository teacherRepository;

    public TeacherResource(TeacherService teacherService, TeacherRepository teacherRepository) {
        this.teacherService = teacherService;
        this.teacherRepository = teacherRepository;
    }

    /**
     * {@code POST  /teachers} : Create a new teacher.
     *
     * @param teacher the teacher to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teacher, or with status {@code 400 (Bad Request)} if the teacher has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teachers")
    public Mono<ResponseEntity<Teacher>> createTeacher(@RequestBody Teacher teacher) throws URISyntaxException {
        log.debug("REST request to save Teacher : {}", teacher);
        if (teacher.getId() != null) {
            throw new BadRequestAlertException("A new teacher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return teacherService
            .save(teacher)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/teachers/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /teachers/:id} : Updates an existing teacher.
     *
     * @param id the id of the teacher to save.
     * @param teacher the teacher to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teacher,
     * or with status {@code 400 (Bad Request)} if the teacher is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teacher couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teachers/{id}")
    public Mono<ResponseEntity<Teacher>> updateTeacher(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Teacher teacher
    ) throws URISyntaxException {
        log.debug("REST request to update Teacher : {}, {}", id, teacher);
        if (teacher.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teacher.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return teacherRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return teacherService
                    .update(teacher)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /teachers/:id} : Partial updates given fields of an existing teacher, field will ignore if it is null
     *
     * @param id the id of the teacher to save.
     * @param teacher the teacher to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teacher,
     * or with status {@code 400 (Bad Request)} if the teacher is not valid,
     * or with status {@code 404 (Not Found)} if the teacher is not found,
     * or with status {@code 500 (Internal Server Error)} if the teacher couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/teachers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Teacher>> partialUpdateTeacher(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Teacher teacher
    ) throws URISyntaxException {
        log.debug("REST request to partial update Teacher partially : {}, {}", id, teacher);
        if (teacher.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teacher.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return teacherRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Teacher> result = teacherService.partialUpdate(teacher);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /teachers} : get all the teachers.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachers in body.
     */
    @GetMapping("/teachers")
    public Mono<ResponseEntity<List<Teacher>>> getAllTeachers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Teachers");
        return teacherService
            .countAll()
            .zipWith(teacherService.findAll(pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity
                    .ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            UriComponentsBuilder.fromHttpRequest(request),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /teachers/:id} : get the "id" teacher.
     *
     * @param id the id of the teacher to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teacher, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/teachers/{id}")
    public Mono<ResponseEntity<Teacher>> getTeacher(@PathVariable Long id) {
        log.debug("REST request to get Teacher : {}", id);
        Mono<Teacher> teacher = teacherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teacher);
    }

    /**
     * {@code DELETE  /teachers/:id} : delete the "id" teacher.
     *
     * @param id the id of the teacher to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/teachers/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteTeacher(@PathVariable Long id) {
        log.debug("REST request to delete Teacher : {}", id);
        return teacherService
            .delete(id)
            .map(result ->
                ResponseEntity
                    .noContent()
                    .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                    .build()
            );
    }
}
