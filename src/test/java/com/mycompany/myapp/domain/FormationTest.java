package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ConcoursTestSamples.*;
import static com.mycompany.myapp.domain.EtablissementTestSamples.*;
import static com.mycompany.myapp.domain.FormationContinueTestSamples.*;
import static com.mycompany.myapp.domain.FormationInitialeTestSamples.*;
import static com.mycompany.myapp.domain.FormationTestSamples.*;
import static com.mycompany.myapp.domain.PriseEnChargeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FormationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formation.class);
        Formation formation1 = getFormationSample1();
        Formation formation2 = new Formation();
        assertThat(formation1).isNotEqualTo(formation2);

        formation2.setId(formation1.getId());
        assertThat(formation1).isEqualTo(formation2);

        formation2 = getFormationSample2();
        assertThat(formation1).isNotEqualTo(formation2);
    }

    @Test
    void etablissementTest() {
        Formation formation = getFormationRandomSampleGenerator();
        Etablissement etablissementBack = getEtablissementRandomSampleGenerator();

        formation.addEtablissement(etablissementBack);
        assertThat(formation.getEtablissements()).containsOnly(etablissementBack);

        formation.removeEtablissement(etablissementBack);
        assertThat(formation.getEtablissements()).doesNotContain(etablissementBack);

        formation.etablissements(new HashSet<>(Set.of(etablissementBack)));
        assertThat(formation.getEtablissements()).containsOnly(etablissementBack);

        formation.setEtablissements(new HashSet<>());
        assertThat(formation.getEtablissements()).doesNotContain(etablissementBack);
    }

    @Test
    void priseEnChargeTest() {
        Formation formation = getFormationRandomSampleGenerator();
        PriseEnCharge priseEnChargeBack = getPriseEnChargeRandomSampleGenerator();

        formation.setPriseEnCharge(priseEnChargeBack);
        assertThat(formation.getPriseEnCharge()).isEqualTo(priseEnChargeBack);
        assertThat(priseEnChargeBack.getFormation()).isEqualTo(formation);

        formation.priseEnCharge(null);
        assertThat(formation.getPriseEnCharge()).isNull();
        assertThat(priseEnChargeBack.getFormation()).isNull();
    }

    @Test
    void formationInitialeTest() {
        Formation formation = getFormationRandomSampleGenerator();
        FormationInitiale formationInitialeBack = getFormationInitialeRandomSampleGenerator();

        formation.setFormationInitiale(formationInitialeBack);
        assertThat(formation.getFormationInitiale()).isEqualTo(formationInitialeBack);
        assertThat(formationInitialeBack.getFormation()).isEqualTo(formation);

        formation.formationInitiale(null);
        assertThat(formation.getFormationInitiale()).isNull();
        assertThat(formationInitialeBack.getFormation()).isNull();
    }

    @Test
    void formationContinueTest() {
        Formation formation = getFormationRandomSampleGenerator();
        FormationContinue formationContinueBack = getFormationContinueRandomSampleGenerator();

        formation.setFormationContinue(formationContinueBack);
        assertThat(formation.getFormationContinue()).isEqualTo(formationContinueBack);
        assertThat(formationContinueBack.getFormation()).isEqualTo(formation);

        formation.formationContinue(null);
        assertThat(formation.getFormationContinue()).isNull();
        assertThat(formationContinueBack.getFormation()).isNull();
    }

    @Test
    void concoursTest() {
        Formation formation = getFormationRandomSampleGenerator();
        Concours concoursBack = getConcoursRandomSampleGenerator();

        formation.setConcours(concoursBack);
        assertThat(formation.getConcours()).isEqualTo(concoursBack);
        assertThat(concoursBack.getFormation()).isEqualTo(formation);

        formation.concours(null);
        assertThat(formation.getConcours()).isNull();
        assertThat(concoursBack.getFormation()).isNull();
    }
}
