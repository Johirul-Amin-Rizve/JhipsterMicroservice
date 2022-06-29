package com.ailaaj.paf.repository;

import com.ailaaj.paf.domain.PafAilaajMedicineSkuMap;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PafAilaajMedicineSkuMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PafAilaajMedicineSkuMapRepository extends JpaRepository<PafAilaajMedicineSkuMap, Long> {}
