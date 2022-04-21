package com.myapp.service;

import com.myapp.domain.Teacher;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link Teacher}.
 */
public interface TeacherService {
    /**
     * Save a teacher.
     *
     * @param teacher the entity to save.
     * @return the persisted entity.
     */
    Mono<Teacher> save(Teacher teacher);

    /**
     * Updates a teacher.
     *
     * @param teacher the entity to update.
     * @return the persisted entity.
     */
    Mono<Teacher> update(Teacher teacher);

    /**
     * Partially updates a teacher.
     *
     * @param teacher the entity to update partially.
     * @return the persisted entity.
     */
    Mono<Teacher> partialUpdate(Teacher teacher);

    /**
     * Get all the teachers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<Teacher> findAll(Pageable pageable);

    /**
     * Returns the number of teachers available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" teacher.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<Teacher> findOne(Long id);

    /**
     * Delete the "id" teacher.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
