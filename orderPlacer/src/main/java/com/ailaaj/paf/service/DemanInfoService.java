package com.ailaaj.paf.service;

import com.ailaaj.paf.domain.DemanInfo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DemanInfo}.
 */
public interface DemanInfoService {
    /**
     * Save a demanInfo.
     *
     * @param demanInfo the entity to save.
     * @return the persisted entity.
     */
    DemanInfo save(DemanInfo demanInfo);

    /**
     * Updates a demanInfo.
     *
     * @param demanInfo the entity to update.
     * @return the persisted entity.
     */
    DemanInfo update(DemanInfo demanInfo);

    /**
     * Partially updates a demanInfo.
     *
     * @param demanInfo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemanInfo> partialUpdate(DemanInfo demanInfo);

    /**
     * Get all the demanInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemanInfo> findAll(Pageable pageable);

    /**
     * Get the "id" demanInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemanInfo> findOne(Long id);

    /**
     * Delete the "id" demanInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
