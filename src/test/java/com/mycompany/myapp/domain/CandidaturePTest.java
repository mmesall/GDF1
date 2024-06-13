package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CandidaturePTestSamples.*;
import static com.mycompany.myapp.domain.EtablissementTestSamples.*;
import static com.mycompany.myapp.domain.FormationContinueTestSamples.*;
import static com.mycompany.myapp.domain.ProfessionnelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CandidaturePTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidatureP.class);
        CandidatureP candidatureP1 = getCandidaturePSample1();
        CandidatureP candidatureP2 = new CandidatureP();
        assertThat(candidatureP1).isNotEqualTo(candidatureP2);

        candidatureP2.setId(candidatureP1.getId());
        assertThat(candidatureP1).isEqualTo(candidatureP2);

        candidatureP2 = getCandidaturePSample2();
        assertThat(candidatureP1).isNotEqualTo(candidatureP2);
    }

    @Test
    void professionnelTest() {
        CandidatureP candidatureP = getCandidaturePRandomSampleGenerator();
        Professionnel professionnelBack = getProfessionnelRandomSampleGenerator();

        candidatureP.setProfessionnel(professionnelBack);
        assertThat(candidatureP.getProfessionnel()).isEqualTo(professionnelBack);

        candidatureP.professionnel(null);
        assertThat(candidatureP.getProfessionnel()).isNull();
    }

    @Test
    void formationContinueTest() {
        CandidatureP candidatureP = getCandidaturePRandomSampleGenerator();
        FormationContinue formationContinueBack = getFormationContinueRandomSampleGenerator();

        candidatureP.setFormationContinue(formationContinueBack);
        assertThat(candidatureP.getFormationContinue()).isEqualTo(formationContinueBack);

        candidatureP.formationContinue(null);
        assertThat(candidatureP.getFormationContinue()).isNull();
    }

    @Test
    void etablissementTest() {
        CandidatureP candidatureP = getCandidaturePRandomSampleGenerator();
        Etablissement etablissementBack = getEtablissementRandomSampleGenerator();

        candidatureP.setEtablissement(etablissementBack);
        assertThat(candidatureP.getEtablissement()).isEqualTo(etablissementBack);

        candidatureP.etablissement(null);
        assertThat(candidatureP.getEtablissement()).isNull();
    }
}
