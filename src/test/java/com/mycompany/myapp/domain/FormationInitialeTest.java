package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CandidatureETestSamples.*;
import static com.mycompany.myapp.domain.FormationInitialeTestSamples.*;
import static com.mycompany.myapp.domain.FormationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FormationInitialeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormationInitiale.class);
        FormationInitiale formationInitiale1 = getFormationInitialeSample1();
        FormationInitiale formationInitiale2 = new FormationInitiale();
        assertThat(formationInitiale1).isNotEqualTo(formationInitiale2);

        formationInitiale2.setId(formationInitiale1.getId());
        assertThat(formationInitiale1).isEqualTo(formationInitiale2);

        formationInitiale2 = getFormationInitialeSample2();
        assertThat(formationInitiale1).isNotEqualTo(formationInitiale2);
    }

    @Test
    void formationTest() {
        FormationInitiale formationInitiale = getFormationInitialeRandomSampleGenerator();
        Formation formationBack = getFormationRandomSampleGenerator();

        formationInitiale.setFormation(formationBack);
        assertThat(formationInitiale.getFormation()).isEqualTo(formationBack);

        formationInitiale.formation(null);
        assertThat(formationInitiale.getFormation()).isNull();
    }

    @Test
    void candidatureETest() {
        FormationInitiale formationInitiale = getFormationInitialeRandomSampleGenerator();
        CandidatureE candidatureEBack = getCandidatureERandomSampleGenerator();

        formationInitiale.addCandidatureE(candidatureEBack);
        assertThat(formationInitiale.getCandidatureES()).containsOnly(candidatureEBack);
        assertThat(candidatureEBack.getFormationInitiale()).isEqualTo(formationInitiale);

        formationInitiale.removeCandidatureE(candidatureEBack);
        assertThat(formationInitiale.getCandidatureES()).doesNotContain(candidatureEBack);
        assertThat(candidatureEBack.getFormationInitiale()).isNull();

        formationInitiale.candidatureES(new HashSet<>(Set.of(candidatureEBack)));
        assertThat(formationInitiale.getCandidatureES()).containsOnly(candidatureEBack);
        assertThat(candidatureEBack.getFormationInitiale()).isEqualTo(formationInitiale);

        formationInitiale.setCandidatureES(new HashSet<>());
        assertThat(formationInitiale.getCandidatureES()).doesNotContain(candidatureEBack);
        assertThat(candidatureEBack.getFormationInitiale()).isNull();
    }
}
