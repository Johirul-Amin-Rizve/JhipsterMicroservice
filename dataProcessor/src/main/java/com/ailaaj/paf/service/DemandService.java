package com.ailaaj.paf.service;

import com.ailaaj.paf.domain.Demand;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Demand}.
 */
public interface DemandService {
    /**
     * Save a demand.
     *
     * @param demand the entity to save.
     * @return the persisted entity.
     */
    Demand save(Demand demand);

    /**
     * Updates a demand.
     *
     * @param demand the entity to update.
     * @return the persisted entity.
     */
    Demand update(Demand demand);

    /**
     * Partially updates a demand.
     *
     * @param demand the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Demand> partialUpdate(Demand demand);

    /**
     * Get all the demands.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Demand> findAll(Pageable pageable);

    /**
     * Get the "id" demand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Demand> findOne(Long id);

    /**
     * Delete the "id" demand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
