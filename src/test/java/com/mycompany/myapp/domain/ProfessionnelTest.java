package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CandidaturePTestSamples.*;
import static com.mycompany.myapp.domain.DemandeurTestSamples.*;
import static com.mycompany.myapp.domain.DiplomeTestSamples.*;
import static com.mycompany.myapp.domain.DossierTestSamples.*;
import static com.mycompany.myapp.domain.ExperienceTestSamples.*;
import static com.mycompany.myapp.domain.ProfessionnelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProfessionnelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Professionnel.class);
        Professionnel professionnel1 = getProfessionnelSample1();
        Professionnel professionnel2 = new Professionnel();
        assertThat(professionnel1).isNotEqualTo(professionnel2);

        professionnel2.setId(professionnel1.getId());
        assertThat(professionnel1).isEqualTo(professionnel2);

        professionnel2 = getProfessionnelSample2();
        assertThat(professionnel1).isNotEqualTo(professionnel2);
    }

    @Test
    void diplomeTest() {
        Professionnel professionnel = getProfessionnelRandomSampleGenerator();
        Diplome diplomeBack = getDiplomeRandomSampleGenerator();

        professionnel.addDiplome(diplomeBack);
        assertThat(professionnel.getDiplomes()).containsOnly(diplomeBack);
        assertThat(diplomeBack.getProfessionnel()).isEqualTo(professionnel);

        professionnel.removeDiplome(diplomeBack);
        assertThat(professionnel.getDiplomes()).doesNotContain(diplomeBack);
        assertThat(diplomeBack.getProfessionnel()).isNull();

        professionnel.diplomes(new HashSet<>(Set.of(diplomeBack)));
        assertThat(professionnel.getDiplomes()).containsOnly(diplomeBack);
        assertThat(diplomeBack.getProfessionnel()).isEqualTo(professionnel);

        professionnel.setDiplomes(new HashSet<>());
        assertThat(professionnel.getDiplomes()).doesNotContain(diplomeBack);
        assertThat(diplomeBack.getProfessionnel()).isNull();
    }

    @Test
    void experienceTest() {
        Professionnel professionnel = getProfessionnelRandomSampleGenerator();
        Experience experienceBack = getExperienceRandomSampleGenerator();

        professionnel.addExperience(experienceBack);
        assertThat(professionnel.getExperiences()).containsOnly(experienceBack);
        assertThat(experienceBack.getProfessionnel()).isEqualTo(professionnel);

        professionnel.removeExperience(experienceBack);
        assertThat(professionnel.getExperiences()).doesNotContain(experienceBack);
        assertThat(experienceBack.getProfessionnel()).isNull();

        professionnel.experiences(new HashSet<>(Set.of(experienceBack)));
        assertThat(professionnel.getExperiences()).containsOnly(experienceBack);
        assertThat(experienceBack.getProfessionnel()).isEqualTo(professionnel);

        professionnel.setExperiences(new HashSet<>());
        assertThat(professionnel.getExperiences()).doesNotContain(experienceBack);
        assertThat(experienceBack.getProfessionnel()).isNull();
    }

    @Test
    void candidaturePTest() {
        Professionnel professionnel = getProfessionnelRandomSampleGenerator();
        CandidatureP candidaturePBack = getCandidaturePRandomSampleGenerator();

        professionnel.addCandidatureP(candidaturePBack);
        assertThat(professionnel.getCandidaturePS()).containsOnly(candidaturePBack);
        assertThat(candidaturePBack.getProfessionnel()).isEqualTo(professionnel);

        professionnel.removeCandidatureP(candidaturePBack);
        assertThat(professionnel.getCandidaturePS()).doesNotContain(candidaturePBack);
        assertThat(candidaturePBack.getProfessionnel()).isNull();

        professionnel.candidaturePS(new HashSet<>(Set.of(candidaturePBack)));
        assertThat(professionnel.getCandidaturePS()).containsOnly(candidaturePBack);
        assertThat(candidaturePBack.getProfessionnel()).isEqualTo(professionnel);

        professionnel.setCandidaturePS(new HashSet<>());
        assertThat(professionnel.getCandidaturePS()).doesNotContain(candidaturePBack);
        assertThat(candidaturePBack.getProfessionnel()).isNull();
    }

    @Test
    void dossierTest() {
        Professionnel professionnel = getProfessionnelRandomSampleGenerator();
        Dossier dossierBack = getDossierRandomSampleGenerator();

        professionnel.setDossier(dossierBack);
        assertThat(professionnel.getDossier()).isEqualTo(dossierBack);
        assertThat(dossierBack.getProfessionnel()).isEqualTo(professionnel);

        professionnel.dossier(null);
        assertThat(professionnel.getDossier()).isNull();
        assertThat(dossierBack.getProfessionnel()).isNull();
    }

    @Test
    void demandeurTest() {
        Professionnel professionnel = getProfessionnelRandomSampleGenerator();
        Demandeur demandeurBack = getDemandeurRandomSampleGenerator();

        professionnel.setDemandeur(demandeurBack);
        assertThat(professionnel.getDemandeur()).isEqualTo(demandeurBack);
        assertThat(demandeurBack.getProfessionnel()).isEqualTo(professionnel);

        professionnel.demandeur(null);
        assertThat(professionnel.getDemandeur()).isNull();
        assertThat(demandeurBack.getProfessionnel()).isNull();
    }
}
