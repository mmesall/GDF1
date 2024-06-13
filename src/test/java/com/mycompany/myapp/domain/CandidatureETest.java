package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CandidatureETestSamples.*;
import static com.mycompany.myapp.domain.EleveTestSamples.*;
import static com.mycompany.myapp.domain.EtablissementTestSamples.*;
import static com.mycompany.myapp.domain.EtudiantTestSamples.*;
import static com.mycompany.myapp.domain.FormationInitialeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CandidatureETest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidatureE.class);
        CandidatureE candidatureE1 = getCandidatureESample1();
        CandidatureE candidatureE2 = new CandidatureE();
        assertThat(candidatureE1).isNotEqualTo(candidatureE2);

        candidatureE2.setId(candidatureE1.getId());
        assertThat(candidatureE1).isEqualTo(candidatureE2);

        candidatureE2 = getCandidatureESample2();
        assertThat(candidatureE1).isNotEqualTo(candidatureE2);
    }

    @Test
    void eleveTest() {
        CandidatureE candidatureE = getCandidatureERandomSampleGenerator();
        Eleve eleveBack = getEleveRandomSampleGenerator();

        candidatureE.setEleve(eleveBack);
        assertThat(candidatureE.getEleve()).isEqualTo(eleveBack);

        candidatureE.eleve(null);
        assertThat(candidatureE.getEleve()).isNull();
    }

    @Test
    void etudiantTest() {
        CandidatureE candidatureE = getCandidatureERandomSampleGenerator();
        Etudiant etudiantBack = getEtudiantRandomSampleGenerator();

        candidatureE.setEtudiant(etudiantBack);
        assertThat(candidatureE.getEtudiant()).isEqualTo(etudiantBack);

        candidatureE.etudiant(null);
        assertThat(candidatureE.getEtudiant()).isNull();
    }

    @Test
    void formationInitialeTest() {
        CandidatureE candidatureE = getCandidatureERandomSampleGenerator();
        FormationInitiale formationInitialeBack = getFormationInitialeRandomSampleGenerator();

        candidatureE.setFormationInitiale(formationInitialeBack);
        assertThat(candidatureE.getFormationInitiale()).isEqualTo(formationInitialeBack);

        candidatureE.formationInitiale(null);
        assertThat(candidatureE.getFormationInitiale()).isNull();
    }

    @Test
    void etablissementTest() {
        CandidatureE candidatureE = getCandidatureERandomSampleGenerator();
        Etablissement etablissementBack = getEtablissementRandomSampleGenerator();

        candidatureE.setEtablissement(etablissementBack);
        assertThat(candidatureE.getEtablissement()).isEqualTo(etablissementBack);

        candidatureE.etablissement(null);
        assertThat(candidatureE.getEtablissement()).isNull();
    }
}
