package com.ailaaj.paf.service.impl;

import com.ailaaj.paf.domain.MedicineSoldPerDemand;
import com.ailaaj.paf.repository.MedicineSoldPerDemandRepository;
import com.ailaaj.paf.service.MedicineSoldPerDemandService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MedicineSoldPerDemand}.
 */
@Service
@Transactional
public class MedicineSoldPerDemandServiceImpl implements MedicineSoldPerDemandService {

    private final Logger log = LoggerFactory.getLogger(MedicineSoldPerDemandServiceImpl.class);

    private final MedicineSoldPerDemandRepository medicineSoldPerDemandRepository;

    public MedicineSoldPerDemandServiceImpl(MedicineSoldPerDemandRepository medicineSoldPerDemandRepository) {
        this.medicineSoldPerDemandRepository = medicineSoldPerDemandRepository;
    }

    @Override
    public MedicineSoldPerDemand save(MedicineSoldPerDemand medicineSoldPerDemand) {
        log.debug("Request to save MedicineSoldPerDemand : {}", medicineSoldPerDemand);
        return medicineSoldPerDemandRepository.save(medicineSoldPerDemand);
    }

    @Override
    public MedicineSoldPerDemand update(MedicineSoldPerDemand medicineSoldPerDemand) {
        log.debug("Request to save MedicineSoldPerDemand : {}", medicineSoldPerDemand);
        return medicineSoldPerDemandRepository.save(medicineSoldPerDemand);
    }

    @Override
    public Optional<MedicineSoldPerDemand> partialUpdate(MedicineSoldPerDemand medicineSoldPerDemand) {
        log.debug("Request to partially update MedicineSoldPerDemand : {}", medicineSoldPerDemand);

        return medicineSoldPerDemandRepository
            .findById(medicineSoldPerDemand.getId())
            .map(existingMedicineSoldPerDemand -> {
                if (medicineSoldPerDemand.getMedicineId() != null) {
                    existingMedicineSoldPerDemand.setMedicineId(medicineSoldPerDemand.getMedicineId());
                }
                if (medicineSoldPerDemand.getMedicineName() != null) {
                    existingMedicineSoldPerDemand.setMedicineName(medicineSoldPerDemand.getMedicineName());
                }
                if (medicineSoldPerDemand.getMedicineOrderQuantity() != null) {
                    existingMedicineSoldPerDemand.setMedicineOrderQuantity(medicineSoldPerDemand.getMedicineOrderQuantity());
                }
                if (medicineSoldPerDemand.getUnitPrice() != null) {
                    existingMedicineSoldPerDemand.setUnitPrice(medicineSoldPerDemand.getUnitPrice());
                }
                if (medicineSoldPerDemand.getDiscountedPrice() != null) {
                    existingMedicineSoldPerDemand.setDiscountedPrice(medicineSoldPerDemand.getDiscountedPrice());
                }
                if (medicineSoldPerDemand.getIssuedQuantity() != null) {
                    existingMedicineSoldPerDemand.setIssuedQuantity(medicineSoldPerDemand.getIssuedQuantity());
                }
                if (medicineSoldPerDemand.getReturnQuantity() != null) {
                    existingMedicineSoldPerDemand.setReturnQuantity(medicineSoldPerDemand.getReturnQuantity());
                }
                if (medicineSoldPerDemand.getSellingDate() != null) {
                    existingMedicineSoldPerDemand.setSellingDate(medicineSoldPerDemand.getSellingDate());
                }

                return existingMedicineSoldPerDemand;
            })
            .map(medicineSoldPerDemandRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicineSoldPerDemand> findAll(Pageable pageable) {
        log.debug("Request to get all MedicineSoldPerDemands");
        return medicineSoldPerDemandRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicineSoldPerDemand> findOne(Long id) {
        log.debug("Request to get MedicineSoldPerDemand : {}", id);
        return medicineSoldPerDemandRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicineSoldPerDemand : {}", id);
        medicineSoldPerDemandRepository.deleteById(id);
    }
}
