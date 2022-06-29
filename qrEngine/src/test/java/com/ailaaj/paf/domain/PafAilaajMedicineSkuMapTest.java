package com.ailaaj.paf.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ailaaj.paf.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PafAilaajMedicineSkuMapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PafAilaajMedicineSkuMap.class);
        PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap1 = new PafAilaajMedicineSkuMap();
        pafAilaajMedicineSkuMap1.setId(1L);
        PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap2 = new PafAilaajMedicineSkuMap();
        pafAilaajMedicineSkuMap2.setId(pafAilaajMedicineSkuMap1.getId());
        assertThat(pafAilaajMedicineSkuMap1).isEqualTo(pafAilaajMedicineSkuMap2);
        pafAilaajMedicineSkuMap2.setId(2L);
        assertThat(pafAilaajMedicineSkuMap1).isNotEqualTo(pafAilaajMedicineSkuMap2);
        pafAilaajMedicineSkuMap1.setId(null);
        assertThat(pafAilaajMedicineSkuMap1).isNotEqualTo(pafAilaajMedicineSkuMap2);
    }
}
