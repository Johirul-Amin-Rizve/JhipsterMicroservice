package com.ailaaj.paf.repository;

import com.ailaaj.paf.domain.DemanInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DemanInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemanInfoRepository extends JpaRepository<DemanInfo, Long> {}
