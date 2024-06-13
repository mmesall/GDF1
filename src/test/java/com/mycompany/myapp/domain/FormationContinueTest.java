package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CandidaturePTestSamples.*;
import static com.mycompany.myapp.domain.FormationContinueTestSamples.*;
import static com.mycompany.myapp.domain.FormationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FormationContinueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormationContinue.class);
        FormationContinue formationContinue1 = getFormationContinueSample1();
        FormationContinue formationContinue2 = new FormationContinue();
        assertThat(formationContinue1).isNotEqualTo(formationContinue2);

        formationContinue2.setId(formationContinue1.getId());
        assertThat(formationContinue1).isEqualTo(formationContinue2);

        formationContinue2 = getFormationContinueSample2();
        assertThat(formationContinue1).isNotEqualTo(formationContinue2);
    }

    @Test
    void formationTest() {
        FormationContinue formationContinue = getFormationContinueRandomSampleGenerator();
        Formation formationBack = getFormationRandomSampleGenerator();

        formationContinue.setFormation(formationBack);
        assertThat(formationContinue.getFormation()).isEqualTo(formationBack);

        formationContinue.formation(null);
        assertThat(formationContinue.getFormation()).isNull();
    }

    @Test
    void candidaturePTest() {
        FormationContinue formationContinue = getFormationContinueRandomSampleGenerator();
        CandidatureP candidaturePBack = getCandidaturePRandomSampleGenerator();

        formationContinue.addCandidatureP(candidaturePBack);
        assertThat(formationContinue.getCandidaturePS()).containsOnly(candidaturePBack);
        assertThat(candidaturePBack.getFormationContinue()).isEqualTo(formationContinue);

        formationContinue.removeCandidatureP(candidaturePBack);
        assertThat(formationContinue.getCandidaturePS()).doesNotContain(candidaturePBack);
        assertThat(candidaturePBack.getFormationContinue()).isNull();

        formationContinue.candidaturePS(new HashSet<>(Set.of(candidaturePBack)));
        assertThat(formationContinue.getCandidaturePS()).containsOnly(candidaturePBack);
        assertThat(candidaturePBack.getFormationContinue()).isEqualTo(formationContinue);

        formationContinue.setCandidaturePS(new HashSet<>());
        assertThat(formationContinue.getCandidaturePS()).doesNotContain(candidaturePBack);
        assertThat(candidaturePBack.getFormationContinue()).isNull();
    }
}
