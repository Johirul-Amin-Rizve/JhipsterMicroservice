package com.ailaaj.paf.service;

import com.ailaaj.paf.domain.MedicineSoldPerDemand;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MedicineSoldPerDemand}.
 */
public interface MedicineSoldPerDemandService {
    /**
     * Save a medicineSoldPerDemand.
     *
     * @param medicineSoldPerDemand the entity to save.
     * @return the persisted entity.
     */
    MedicineSoldPerDemand save(MedicineSoldPerDemand medicineSoldPerDemand);

    /**
     * Updates a medicineSoldPerDemand.
     *
     * @param medicineSoldPerDemand the entity to update.
     * @return the persisted entity.
     */
    MedicineSoldPerDemand update(MedicineSoldPerDemand medicineSoldPerDemand);

    /**
     * Partially updates a medicineSoldPerDemand.
     *
     * @param medicineSoldPerDemand the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MedicineSoldPerDemand> partialUpdate(MedicineSoldPerDemand medicineSoldPerDemand);

    /**
     * Get all the medicineSoldPerDemands.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicineSoldPerDemand> findAll(Pageable pageable);

    /**
     * Get the "id" medicineSoldPerDemand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicineSoldPerDemand> findOne(Long id);

    /**
     * Delete the "id" medicineSoldPerDemand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
