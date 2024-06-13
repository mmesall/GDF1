package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.BailleurTestSamples.*;
import static com.mycompany.myapp.domain.PriseEnChargeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BailleurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bailleur.class);
        Bailleur bailleur1 = getBailleurSample1();
        Bailleur bailleur2 = new Bailleur();
        assertThat(bailleur1).isNotEqualTo(bailleur2);

        bailleur2.setId(bailleur1.getId());
        assertThat(bailleur1).isEqualTo(bailleur2);

        bailleur2 = getBailleurSample2();
        assertThat(bailleur1).isNotEqualTo(bailleur2);
    }

    @Test
    void priseEnChargeTest() {
        Bailleur bailleur = getBailleurRandomSampleGenerator();
        PriseEnCharge priseEnChargeBack = getPriseEnChargeRandomSampleGenerator();

        bailleur.addPriseEnCharge(priseEnChargeBack);
        assertThat(bailleur.getPriseEnCharges()).containsOnly(priseEnChargeBack);
        assertThat(priseEnChargeBack.getBailleur()).isEqualTo(bailleur);

        bailleur.removePriseEnCharge(priseEnChargeBack);
        assertThat(bailleur.getPriseEnCharges()).doesNotContain(priseEnChargeBack);
        assertThat(priseEnChargeBack.getBailleur()).isNull();

        bailleur.priseEnCharges(new HashSet<>(Set.of(priseEnChargeBack)));
        assertThat(bailleur.getPriseEnCharges()).containsOnly(priseEnChargeBack);
        assertThat(priseEnChargeBack.getBailleur()).isEqualTo(bailleur);

        bailleur.setPriseEnCharges(new HashSet<>());
        assertThat(bailleur.getPriseEnCharges()).doesNotContain(priseEnChargeBack);
        assertThat(priseEnChargeBack.getBailleur()).isNull();
    }
}
