package com.ailaaj.paf.service.impl;

import com.ailaaj.paf.domain.PafAilaajMedicineSkuMap;
import com.ailaaj.paf.repository.PafAilaajMedicineSkuMapRepository;
import com.ailaaj.paf.service.PafAilaajMedicineSkuMapService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PafAilaajMedicineSkuMap}.
 */
@Service
@Transactional
public class PafAilaajMedicineSkuMapServiceImpl implements PafAilaajMedicineSkuMapService {

    private final Logger log = LoggerFactory.getLogger(PafAilaajMedicineSkuMapServiceImpl.class);

    private final PafAilaajMedicineSkuMapRepository pafAilaajMedicineSkuMapRepository;

    public PafAilaajMedicineSkuMapServiceImpl(PafAilaajMedicineSkuMapRepository pafAilaajMedicineSkuMapRepository) {
        this.pafAilaajMedicineSkuMapRepository = pafAilaajMedicineSkuMapRepository;
    }

    @Override
    public PafAilaajMedicineSkuMap save(PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap) {
        log.debug("Request to save PafAilaajMedicineSkuMap : {}", pafAilaajMedicineSkuMap);
        return pafAilaajMedicineSkuMapRepository.save(pafAilaajMedicineSkuMap);
    }

    @Override
    public PafAilaajMedicineSkuMap update(PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap) {
        log.debug("Request to save PafAilaajMedicineSkuMap : {}", pafAilaajMedicineSkuMap);
        return pafAilaajMedicineSkuMapRepository.save(pafAilaajMedicineSkuMap);
    }

    @Override
    public Optional<PafAilaajMedicineSkuMap> partialUpdate(PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap) {
        log.debug("Request to partially update PafAilaajMedicineSkuMap : {}", pafAilaajMedicineSkuMap);

        return pafAilaajMedicineSkuMapRepository
            .findById(pafAilaajMedicineSkuMap.getId())
            .map(existingPafAilaajMedicineSkuMap -> {
                if (pafAilaajMedicineSkuMap.getAilaajMedicineId() != null) {
                    existingPafAilaajMedicineSkuMap.setAilaajMedicineId(pafAilaajMedicineSkuMap.getAilaajMedicineId());
                }
                if (pafAilaajMedicineSkuMap.getPafMedicineId() != null) {
                    existingPafAilaajMedicineSkuMap.setPafMedicineId(pafAilaajMedicineSkuMap.getPafMedicineId());
                }
                if (pafAilaajMedicineSkuMap.getPafMedicineName() != null) {
                    existingPafAilaajMedicineSkuMap.setPafMedicineName(pafAilaajMedicineSkuMap.getPafMedicineName());
                }
                if (pafAilaajMedicineSkuMap.getMedicineName() != null) {
                    existingPafAilaajMedicineSkuMap.setMedicineName(pafAilaajMedicineSkuMap.getMedicineName());
                }
                if (pafAilaajMedicineSkuMap.getcFactorbrand() != null) {
                    existingPafAilaajMedicineSkuMap.setcFactorbrand(pafAilaajMedicineSkuMap.getcFactorbrand());
                }
                if (pafAilaajMedicineSkuMap.getSupplier() != null) {
                    existingPafAilaajMedicineSkuMap.setSupplier(pafAilaajMedicineSkuMap.getSupplier());
                }

                return existingPafAilaajMedicineSkuMap;
            })
            .map(pafAilaajMedicineSkuMapRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PafAilaajMedicineSkuMap> findAll(Pageable pageable) {
        log.debug("Request to get all PafAilaajMedicineSkuMaps");
        return pafAilaajMedicineSkuMapRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PafAilaajMedicineSkuMap> findOne(Long id) {
        log.debug("Request to get PafAilaajMedicineSkuMap : {}", id);
        return pafAilaajMedicineSkuMapRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PafAilaajMedicineSkuMap : {}", id);
        pafAilaajMedicineSkuMapRepository.deleteById(id);
    }
}
