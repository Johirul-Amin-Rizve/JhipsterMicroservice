package com.ailaaj.paf.service.impl;

import com.ailaaj.paf.domain.DemanInfo;
import com.ailaaj.paf.repository.DemanInfoRepository;
import com.ailaaj.paf.service.DemanInfoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DemanInfo}.
 */
@Service
@Transactional
public class DemanInfoServiceImpl implements DemanInfoService {

    private final Logger log = LoggerFactory.getLogger(DemanInfoServiceImpl.class);

    private final DemanInfoRepository demanInfoRepository;

    public DemanInfoServiceImpl(DemanInfoRepository demanInfoRepository) {
        this.demanInfoRepository = demanInfoRepository;
    }

    @Override
    public DemanInfo save(DemanInfo demanInfo) {
        log.debug("Request to save DemanInfo : {}", demanInfo);
        return demanInfoRepository.save(demanInfo);
    }

    @Override
    public DemanInfo update(DemanInfo demanInfo) {
        log.debug("Request to save DemanInfo : {}", demanInfo);
        return demanInfoRepository.save(demanInfo);
    }

    @Override
    public Optional<DemanInfo> partialUpdate(DemanInfo demanInfo) {
        log.debug("Request to partially update DemanInfo : {}", demanInfo);

        return demanInfoRepository
            .findById(demanInfo.getId())
            .map(existingDemanInfo -> {
                if (demanInfo.getDemandId() != null) {
                    existingDemanInfo.setDemandId(demanInfo.getDemandId());
                }
                if (demanInfo.getDemandStatus() != null) {
                    existingDemanInfo.setDemandStatus(demanInfo.getDemandStatus());
                }
                if (demanInfo.getInvoiceStatus() != null) {
                    existingDemanInfo.setInvoiceStatus(demanInfo.getInvoiceStatus());
                }

                return existingDemanInfo;
            })
            .map(demanInfoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemanInfo> findAll(Pageable pageable) {
        log.debug("Request to get all DemanInfos");
        return demanInfoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemanInfo> findOne(Long id) {
        log.debug("Request to get DemanInfo : {}", id);
        return demanInfoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemanInfo : {}", id);
        demanInfoRepository.deleteById(id);
    }
}
