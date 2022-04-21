package com.myapp.service;

import com.myapp.domain.Department;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link Department}.
 */
public interface DepartmentService {
    /**
     * Save a department.
     *
     * @param department the entity to save.
     * @return the persisted entity.
     */
    Mono<Department> save(Department department);

    /**
     * Updates a department.
     *
     * @param department the entity to update.
     * @return the persisted entity.
     */
    Mono<Department> update(Department department);

    /**
     * Partially updates a department.
     *
     * @param department the entity to update partially.
     * @return the persisted entity.
     */
    Mono<Department> partialUpdate(Department department);

    /**
     * Get all the departments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<Department> findAll(Pageable pageable);

    /**
     * Returns the number of departments available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" department.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<Department> findOne(Long id);

    /**
     * Delete the "id" department.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
