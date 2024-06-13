package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CandidatureETestSamples.*;
import static com.mycompany.myapp.domain.DemandeurTestSamples.*;
import static com.mycompany.myapp.domain.DiplomeTestSamples.*;
import static com.mycompany.myapp.domain.DossierTestSamples.*;
import static com.mycompany.myapp.domain.EtudiantTestSamples.*;
import static com.mycompany.myapp.domain.ExperienceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EtudiantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etudiant.class);
        Etudiant etudiant1 = getEtudiantSample1();
        Etudiant etudiant2 = new Etudiant();
        assertThat(etudiant1).isNotEqualTo(etudiant2);

        etudiant2.setId(etudiant1.getId());
        assertThat(etudiant1).isEqualTo(etudiant2);

        etudiant2 = getEtudiantSample2();
        assertThat(etudiant1).isNotEqualTo(etudiant2);
    }

    @Test
    void diplomeTest() {
        Etudiant etudiant = getEtudiantRandomSampleGenerator();
        Diplome diplomeBack = getDiplomeRandomSampleGenerator();

        etudiant.addDiplome(diplomeBack);
        assertThat(etudiant.getDiplomes()).containsOnly(diplomeBack);
        assertThat(diplomeBack.getEtudiant()).isEqualTo(etudiant);

        etudiant.removeDiplome(diplomeBack);
        assertThat(etudiant.getDiplomes()).doesNotContain(diplomeBack);
        assertThat(diplomeBack.getEtudiant()).isNull();

        etudiant.diplomes(new HashSet<>(Set.of(diplomeBack)));
        assertThat(etudiant.getDiplomes()).containsOnly(diplomeBack);
        assertThat(diplomeBack.getEtudiant()).isEqualTo(etudiant);

        etudiant.setDiplomes(new HashSet<>());
        assertThat(etudiant.getDiplomes()).doesNotContain(diplomeBack);
        assertThat(diplomeBack.getEtudiant()).isNull();
    }

    @Test
    void experienceTest() {
        Etudiant etudiant = getEtudiantRandomSampleGenerator();
        Experience experienceBack = getExperienceRandomSampleGenerator();

        etudiant.addExperience(experienceBack);
        assertThat(etudiant.getExperiences()).containsOnly(experienceBack);
        assertThat(experienceBack.getEtudiant()).isEqualTo(etudiant);

        etudiant.removeExperience(experienceBack);
        assertThat(etudiant.getExperiences()).doesNotContain(experienceBack);
        assertThat(experienceBack.getEtudiant()).isNull();

        etudiant.experiences(new HashSet<>(Set.of(experienceBack)));
        assertThat(etudiant.getExperiences()).containsOnly(experienceBack);
        assertThat(experienceBack.getEtudiant()).isEqualTo(etudiant);

        etudiant.setExperiences(new HashSet<>());
        assertThat(etudiant.getExperiences()).doesNotContain(experienceBack);
        assertThat(experienceBack.getEtudiant()).isNull();
    }

    @Test
    void candidatureETest() {
        Etudiant etudiant = getEtudiantRandomSampleGenerator();
        CandidatureE candidatureEBack = getCandidatureERandomSampleGenerator();

        etudiant.addCandidatureE(candidatureEBack);
        assertThat(etudiant.getCandidatureES()).containsOnly(candidatureEBack);
        assertThat(candidatureEBack.getEtudiant()).isEqualTo(etudiant);

        etudiant.removeCandidatureE(candidatureEBack);
        assertThat(etudiant.getCandidatureES()).doesNotContain(candidatureEBack);
        assertThat(candidatureEBack.getEtudiant()).isNull();

        etudiant.candidatureES(new HashSet<>(Set.of(candidatureEBack)));
        assertThat(etudiant.getCandidatureES()).containsOnly(candidatureEBack);
        assertThat(candidatureEBack.getEtudiant()).isEqualTo(etudiant);

        etudiant.setCandidatureES(new HashSet<>());
        assertThat(etudiant.getCandidatureES()).doesNotContain(candidatureEBack);
        assertThat(candidatureEBack.getEtudiant()).isNull();
    }

    @Test
    void dossierTest() {
        Etudiant etudiant = getEtudiantRandomSampleGenerator();
        Dossier dossierBack = getDossierRandomSampleGenerator();

        etudiant.setDossier(dossierBack);
        assertThat(etudiant.getDossier()).isEqualTo(dossierBack);
        assertThat(dossierBack.getEtudiant()).isEqualTo(etudiant);

        etudiant.dossier(null);
        assertThat(etudiant.getDossier()).isNull();
        assertThat(dossierBack.getEtudiant()).isNull();
    }

    @Test
    void demandeurTest() {
        Etudiant etudiant = getEtudiantRandomSampleGenerator();
        Demandeur demandeurBack = getDemandeurRandomSampleGenerator();

        etudiant.setDemandeur(demandeurBack);
        assertThat(etudiant.getDemandeur()).isEqualTo(demandeurBack);
        assertThat(demandeurBack.getEtudiant()).isEqualTo(etudiant);

        etudiant.demandeur(null);
        assertThat(etudiant.getDemandeur()).isNull();
        assertThat(demandeurBack.getEtudiant()).isNull();
    }
}
