package com.ailaaj.paf.service.impl;

import com.ailaaj.paf.domain.Demand;
import com.ailaaj.paf.repository.DemandRepository;
import com.ailaaj.paf.service.DemandService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Demand}.
 */
@Service
@Transactional
public class DemandServiceImpl implements DemandService {

    private final Logger log = LoggerFactory.getLogger(DemandServiceImpl.class);

    private final DemandRepository demandRepository;

    public DemandServiceImpl(DemandRepository demandRepository) {
        this.demandRepository = demandRepository;
    }

    @Override
    public Demand save(Demand demand) {
        log.debug("Request to save Demand : {}", demand);
        return demandRepository.save(demand);
    }

    @Override
    public Demand update(Demand demand) {
        log.debug("Request to save Demand : {}", demand);
        return demandRepository.save(demand);
    }

    @Override
    public Optional<Demand> partialUpdate(Demand demand) {
        log.debug("Request to partially update Demand : {}", demand);

        return demandRepository
            .findById(demand.getId())
            .map(existingDemand -> {
                if (demand.getOrderId() != null) {
                    existingDemand.setOrderId(demand.getOrderId());
                }
                if (demand.getOrderStatus() != null) {
                    existingDemand.setOrderStatus(demand.getOrderStatus());
                }
                if (demand.getInvoiceStatus() != null) {
                    existingDemand.setInvoiceStatus(demand.getInvoiceStatus());
                }
                if (demand.getHospitalName() != null) {
                    existingDemand.setHospitalName(demand.getHospitalName());
                }
                if (demand.getCreatedDate() != null) {
                    existingDemand.setCreatedDate(demand.getCreatedDate());
                }

                return existingDemand;
            })
            .map(demandRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Demand> findAll(Pageable pageable) {
        log.debug("Request to get all Demands");
        return demandRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Demand> findOne(Long id) {
        log.debug("Request to get Demand : {}", id);
        return demandRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Demand : {}", id);
        demandRepository.deleteById(id);
    }
}
