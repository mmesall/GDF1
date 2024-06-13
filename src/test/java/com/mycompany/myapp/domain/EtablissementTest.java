package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CandidatureETestSamples.*;
import static com.mycompany.myapp.domain.CandidaturePTestSamples.*;
import static com.mycompany.myapp.domain.EtablissementTestSamples.*;
import static com.mycompany.myapp.domain.FormationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EtablissementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etablissement.class);
        Etablissement etablissement1 = getEtablissementSample1();
        Etablissement etablissement2 = new Etablissement();
        assertThat(etablissement1).isNotEqualTo(etablissement2);

        etablissement2.setId(etablissement1.getId());
        assertThat(etablissement1).isEqualTo(etablissement2);

        etablissement2 = getEtablissementSample2();
        assertThat(etablissement1).isNotEqualTo(etablissement2);
    }

    @Test
    void candidatureETest() {
        Etablissement etablissement = getEtablissementRandomSampleGenerator();
        CandidatureE candidatureEBack = getCandidatureERandomSampleGenerator();

        etablissement.addCandidatureE(candidatureEBack);
        assertThat(etablissement.getCandidatureES()).containsOnly(candidatureEBack);
        assertThat(candidatureEBack.getEtablissement()).isEqualTo(etablissement);

        etablissement.removeCandidatureE(candidatureEBack);
        assertThat(etablissement.getCandidatureES()).doesNotContain(candidatureEBack);
        assertThat(candidatureEBack.getEtablissement()).isNull();

        etablissement.candidatureES(new HashSet<>(Set.of(candidatureEBack)));
        assertThat(etablissement.getCandidatureES()).containsOnly(candidatureEBack);
        assertThat(candidatureEBack.getEtablissement()).isEqualTo(etablissement);

        etablissement.setCandidatureES(new HashSet<>());
        assertThat(etablissement.getCandidatureES()).doesNotContain(candidatureEBack);
        assertThat(candidatureEBack.getEtablissement()).isNull();
    }

    @Test
    void candidaturePTest() {
        Etablissement etablissement = getEtablissementRandomSampleGenerator();
        CandidatureP candidaturePBack = getCandidaturePRandomSampleGenerator();

        etablissement.addCandidatureP(candidaturePBack);
        assertThat(etablissement.getCandidaturePS()).containsOnly(candidaturePBack);
        assertThat(candidaturePBack.getEtablissement()).isEqualTo(etablissement);

        etablissement.removeCandidatureP(candidaturePBack);
        assertThat(etablissement.getCandidaturePS()).doesNotContain(candidaturePBack);
        assertThat(candidaturePBack.getEtablissement()).isNull();

        etablissement.candidaturePS(new HashSet<>(Set.of(candidaturePBack)));
        assertThat(etablissement.getCandidaturePS()).containsOnly(candidaturePBack);
        assertThat(candidaturePBack.getEtablissement()).isEqualTo(etablissement);

        etablissement.setCandidaturePS(new HashSet<>());
        assertThat(etablissement.getCandidaturePS()).doesNotContain(candidaturePBack);
        assertThat(candidaturePBack.getEtablissement()).isNull();
    }

    @Test
    void formationTest() {
        Etablissement etablissement = getEtablissementRandomSampleGenerator();
        Formation formationBack = getFormationRandomSampleGenerator();

        etablissement.addFormation(formationBack);
        assertThat(etablissement.getFormations()).containsOnly(formationBack);
        assertThat(formationBack.getEtablissements()).containsOnly(etablissement);

        etablissement.removeFormation(formationBack);
        assertThat(etablissement.getFormations()).doesNotContain(formationBack);
        assertThat(formationBack.getEtablissements()).doesNotContain(etablissement);

        etablissement.formations(new HashSet<>(Set.of(formationBack)));
        assertThat(etablissement.getFormations()).containsOnly(formationBack);
        assertThat(formationBack.getEtablissements()).containsOnly(etablissement);

        etablissement.setFormations(new HashSet<>());
        assertThat(etablissement.getFormations()).doesNotContain(formationBack);
        assertThat(formationBack.getEtablissements()).doesNotContain(etablissement);
    }
}
