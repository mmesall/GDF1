package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CandidatureETestSamples.*;
import static com.mycompany.myapp.domain.DemandeurTestSamples.*;
import static com.mycompany.myapp.domain.DiplomeTestSamples.*;
import static com.mycompany.myapp.domain.DossierTestSamples.*;
import static com.mycompany.myapp.domain.EleveTestSamples.*;
import static com.mycompany.myapp.domain.ExperienceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EleveTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eleve.class);
        Eleve eleve1 = getEleveSample1();
        Eleve eleve2 = new Eleve();
        assertThat(eleve1).isNotEqualTo(eleve2);

        eleve2.setId(eleve1.getId());
        assertThat(eleve1).isEqualTo(eleve2);

        eleve2 = getEleveSample2();
        assertThat(eleve1).isNotEqualTo(eleve2);
    }

    @Test
    void diplomeTest() {
        Eleve eleve = getEleveRandomSampleGenerator();
        Diplome diplomeBack = getDiplomeRandomSampleGenerator();

        eleve.addDiplome(diplomeBack);
        assertThat(eleve.getDiplomes()).containsOnly(diplomeBack);
        assertThat(diplomeBack.getEleve()).isEqualTo(eleve);

        eleve.removeDiplome(diplomeBack);
        assertThat(eleve.getDiplomes()).doesNotContain(diplomeBack);
        assertThat(diplomeBack.getEleve()).isNull();

        eleve.diplomes(new HashSet<>(Set.of(diplomeBack)));
        assertThat(eleve.getDiplomes()).containsOnly(diplomeBack);
        assertThat(diplomeBack.getEleve()).isEqualTo(eleve);

        eleve.setDiplomes(new HashSet<>());
        assertThat(eleve.getDiplomes()).doesNotContain(diplomeBack);
        assertThat(diplomeBack.getEleve()).isNull();
    }

    @Test
    void experienceTest() {
        Eleve eleve = getEleveRandomSampleGenerator();
        Experience experienceBack = getExperienceRandomSampleGenerator();

        eleve.addExperience(experienceBack);
        assertThat(eleve.getExperiences()).containsOnly(experienceBack);
        assertThat(experienceBack.getEleve()).isEqualTo(eleve);

        eleve.removeExperience(experienceBack);
        assertThat(eleve.getExperiences()).doesNotContain(experienceBack);
        assertThat(experienceBack.getEleve()).isNull();

        eleve.experiences(new HashSet<>(Set.of(experienceBack)));
        assertThat(eleve.getExperiences()).containsOnly(experienceBack);
        assertThat(experienceBack.getEleve()).isEqualTo(eleve);

        eleve.setExperiences(new HashSet<>());
        assertThat(eleve.getExperiences()).doesNotContain(experienceBack);
        assertThat(experienceBack.getEleve()).isNull();
    }

    @Test
    void candidatureETest() {
        Eleve eleve = getEleveRandomSampleGenerator();
        CandidatureE candidatureEBack = getCandidatureERandomSampleGenerator();

        eleve.addCandidatureE(candidatureEBack);
        assertThat(eleve.getCandidatureES()).containsOnly(candidatureEBack);
        assertThat(candidatureEBack.getEleve()).isEqualTo(eleve);

        eleve.removeCandidatureE(candidatureEBack);
        assertThat(eleve.getCandidatureES()).doesNotContain(candidatureEBack);
        assertThat(candidatureEBack.getEleve()).isNull();

        eleve.candidatureES(new HashSet<>(Set.of(candidatureEBack)));
        assertThat(eleve.getCandidatureES()).containsOnly(candidatureEBack);
        assertThat(candidatureEBack.getEleve()).isEqualTo(eleve);

        eleve.setCandidatureES(new HashSet<>());
        assertThat(eleve.getCandidatureES()).doesNotContain(candidatureEBack);
        assertThat(candidatureEBack.getEleve()).isNull();
    }

    @Test
    void dossierTest() {
        Eleve eleve = getEleveRandomSampleGenerator();
        Dossier dossierBack = getDossierRandomSampleGenerator();

        eleve.setDossier(dossierBack);
        assertThat(eleve.getDossier()).isEqualTo(dossierBack);
        assertThat(dossierBack.getEleve()).isEqualTo(eleve);

        eleve.dossier(null);
        assertThat(eleve.getDossier()).isNull();
        assertThat(dossierBack.getEleve()).isNull();
    }

    @Test
    void demandeurTest() {
        Eleve eleve = getEleveRandomSampleGenerator();
        Demandeur demandeurBack = getDemandeurRandomSampleGenerator();

        eleve.setDemandeur(demandeurBack);
        assertThat(eleve.getDemandeur()).isEqualTo(demandeurBack);
        assertThat(demandeurBack.getEleve()).isEqualTo(eleve);

        eleve.demandeur(null);
        assertThat(eleve.getDemandeur()).isNull();
        assertThat(demandeurBack.getEleve()).isNull();
    }
}
