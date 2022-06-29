package com.ailaaj.paf.service;

import com.ailaaj.paf.domain.PafAilaajMedicineSkuMap;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PafAilaajMedicineSkuMap}.
 */
public interface PafAilaajMedicineSkuMapService {
    /**
     * Save a pafAilaajMedicineSkuMap.
     *
     * @param pafAilaajMedicineSkuMap the entity to save.
     * @return the persisted entity.
     */
    PafAilaajMedicineSkuMap save(PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap);

    /**
     * Updates a pafAilaajMedicineSkuMap.
     *
     * @param pafAilaajMedicineSkuMap the entity to update.
     * @return the persisted entity.
     */
    PafAilaajMedicineSkuMap update(PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap);

    /**
     * Partially updates a pafAilaajMedicineSkuMap.
     *
     * @param pafAilaajMedicineSkuMap the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PafAilaajMedicineSkuMap> partialUpdate(PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap);

    /**
     * Get all the pafAilaajMedicineSkuMaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PafAilaajMedicineSkuMap> findAll(Pageable pageable);

    /**
     * Get the "id" pafAilaajMedicineSkuMap.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PafAilaajMedicineSkuMap> findOne(Long id);

    /**
     * Delete the "id" pafAilaajMedicineSkuMap.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
