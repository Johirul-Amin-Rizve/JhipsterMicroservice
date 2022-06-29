package com.ailaaj.paf.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ailaaj.paf.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemanInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemanInfo.class);
        DemanInfo demanInfo1 = new DemanInfo();
        demanInfo1.setId(1L);
        DemanInfo demanInfo2 = new DemanInfo();
        demanInfo2.setId(demanInfo1.getId());
        assertThat(demanInfo1).isEqualTo(demanInfo2);
        demanInfo2.setId(2L);
        assertThat(demanInfo1).isNotEqualTo(demanInfo2);
        demanInfo1.setId(null);
        assertThat(demanInfo1).isNotEqualTo(demanInfo2);
    }
}
