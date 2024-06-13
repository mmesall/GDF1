package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.BailleurTestSamples.*;
import static com.mycompany.myapp.domain.FormationTestSamples.*;
import static com.mycompany.myapp.domain.PriseEnChargeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PriseEnChargeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriseEnCharge.class);
        PriseEnCharge priseEnCharge1 = getPriseEnChargeSample1();
        PriseEnCharge priseEnCharge2 = new PriseEnCharge();
        assertThat(priseEnCharge1).isNotEqualTo(priseEnCharge2);

        priseEnCharge2.setId(priseEnCharge1.getId());
        assertThat(priseEnCharge1).isEqualTo(priseEnCharge2);

        priseEnCharge2 = getPriseEnChargeSample2();
        assertThat(priseEnCharge1).isNotEqualTo(priseEnCharge2);
    }

    @Test
    void formationTest() {
        PriseEnCharge priseEnCharge = getPriseEnChargeRandomSampleGenerator();
        Formation formationBack = getFormationRandomSampleGenerator();

        priseEnCharge.setFormation(formationBack);
        assertThat(priseEnCharge.getFormation()).isEqualTo(formationBack);

        priseEnCharge.formation(null);
        assertThat(priseEnCharge.getFormation()).isNull();
    }

    @Test
    void bailleurTest() {
        PriseEnCharge priseEnCharge = getPriseEnChargeRandomSampleGenerator();
        Bailleur bailleurBack = getBailleurRandomSampleGenerator();

        priseEnCharge.setBailleur(bailleurBack);
        assertThat(priseEnCharge.getBailleur()).isEqualTo(bailleurBack);

        priseEnCharge.bailleur(null);
        assertThat(priseEnCharge.getBailleur()).isNull();
    }
}
