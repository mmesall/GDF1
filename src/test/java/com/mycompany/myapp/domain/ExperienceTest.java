package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DemandeurTestSamples.*;
import static com.mycompany.myapp.domain.EleveTestSamples.*;
import static com.mycompany.myapp.domain.EtudiantTestSamples.*;
import static com.mycompany.myapp.domain.ExperienceTestSamples.*;
import static com.mycompany.myapp.domain.ProfessionnelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExperienceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Experience.class);
        Experience experience1 = getExperienceSample1();
        Experience experience2 = new Experience();
        assertThat(experience1).isNotEqualTo(experience2);

        experience2.setId(experience1.getId());
        assertThat(experience1).isEqualTo(experience2);

        experience2 = getExperienceSample2();
        assertThat(experience1).isNotEqualTo(experience2);
    }

    @Test
    void eleveTest() {
        Experience experience = getExperienceRandomSampleGenerator();
        Eleve eleveBack = getEleveRandomSampleGenerator();

        experience.setEleve(eleveBack);
        assertThat(experience.getEleve()).isEqualTo(eleveBack);

        experience.eleve(null);
        assertThat(experience.getEleve()).isNull();
    }

    @Test
    void etudiantTest() {
        Experience experience = getExperienceRandomSampleGenerator();
        Etudiant etudiantBack = getEtudiantRandomSampleGenerator();

        experience.setEtudiant(etudiantBack);
        assertThat(experience.getEtudiant()).isEqualTo(etudiantBack);

        experience.etudiant(null);
        assertThat(experience.getEtudiant()).isNull();
    }

    @Test
    void professionnelTest() {
        Experience experience = getExperienceRandomSampleGenerator();
        Professionnel professionnelBack = getProfessionnelRandomSampleGenerator();

        experience.setProfessionnel(professionnelBack);
        assertThat(experience.getProfessionnel()).isEqualTo(professionnelBack);

        experience.professionnel(null);
        assertThat(experience.getProfessionnel()).isNull();
    }

    @Test
    void demandeurTest() {
        Experience experience = getExperienceRandomSampleGenerator();
        Demandeur demandeurBack = getDemandeurRandomSampleGenerator();

        experience.setDemandeur(demandeurBack);
        assertThat(experience.getDemandeur()).isEqualTo(demandeurBack);

        experience.demandeur(null);
        assertThat(experience.getDemandeur()).isNull();
    }
}
