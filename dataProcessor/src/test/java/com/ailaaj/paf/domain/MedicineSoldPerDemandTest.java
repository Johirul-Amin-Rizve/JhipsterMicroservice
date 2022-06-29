package com.ailaaj.paf.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ailaaj.paf.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedicineSoldPerDemandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicineSoldPerDemand.class);
        MedicineSoldPerDemand medicineSoldPerDemand1 = new MedicineSoldPerDemand();
        medicineSoldPerDemand1.setId(1L);
        MedicineSoldPerDemand medicineSoldPerDemand2 = new MedicineSoldPerDemand();
        medicineSoldPerDemand2.setId(medicineSoldPerDemand1.getId());
        assertThat(medicineSoldPerDemand1).isEqualTo(medicineSoldPerDemand2);
        medicineSoldPerDemand2.setId(2L);
        assertThat(medicineSoldPerDemand1).isNotEqualTo(medicineSoldPerDemand2);
        medicineSoldPerDemand1.setId(null);
        assertThat(medicineSoldPerDemand1).isNotEqualTo(medicineSoldPerDemand2);
    }
}
