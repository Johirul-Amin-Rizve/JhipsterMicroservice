package com.ailaaj.paf.repository;

import com.ailaaj.paf.domain.Demand;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Demand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandRepository extends JpaRepository<Demand, Long> {}
