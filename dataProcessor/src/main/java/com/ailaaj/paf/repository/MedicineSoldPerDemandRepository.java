package com.ailaaj.paf.repository;

import com.ailaaj.paf.domain.MedicineSoldPerDemand;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MedicineSoldPerDemand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicineSoldPerDemandRepository extends JpaRepository<MedicineSoldPerDemand, Long> {}
