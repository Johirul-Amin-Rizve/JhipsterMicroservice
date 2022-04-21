package com.myapp.service;

import com.myapp.domain.Student;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link Student}.
 */
public interface StudentService {
    /**
     * Save a student.
     *
     * @param student the entity to save.
     * @return the persisted entity.
     */
    Mono<Student> save(Student student);

    /**
     * Updates a student.
     *
     * @param student the entity to update.
     * @return the persisted entity.
     */
    Mono<Student> update(Student student);

    /**
     * Partially updates a student.
     *
     * @param student the entity to update partially.
     * @return the persisted entity.
     */
    Mono<Student> partialUpdate(Student student);

    /**
     * Get all the students.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<Student> findAll(Pageable pageable);

    /**
     * Returns the number of students available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" student.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<Student> findOne(Long id);

    /**
     * Delete the "id" student.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
