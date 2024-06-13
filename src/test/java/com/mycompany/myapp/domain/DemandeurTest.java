package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DemandeurTestSamples.*;
import static com.mycompany.myapp.domain.DiplomeTestSamples.*;
import static com.mycompany.myapp.domain.DossierTestSamples.*;
import static com.mycompany.myapp.domain.EleveTestSamples.*;
import static com.mycompany.myapp.domain.EtudiantTestSamples.*;
import static com.mycompany.myapp.domain.ExperienceTestSamples.*;
import static com.mycompany.myapp.domain.ProfessionnelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DemandeurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Demandeur.class);
        Demandeur demandeur1 = getDemandeurSample1();
        Demandeur demandeur2 = new Demandeur();
        assertThat(demandeur1).isNotEqualTo(demandeur2);

        demandeur2.setId(demandeur1.getId());
        assertThat(demandeur1).isEqualTo(demandeur2);

        demandeur2 = getDemandeurSample2();
        assertThat(demandeur1).isNotEqualTo(demandeur2);
    }

    @Test
    void dossierTest() {
        Demandeur demandeur = getDemandeurRandomSampleGenerator();
        Dossier dossierBack = getDossierRandomSampleGenerator();

        demandeur.setDossier(dossierBack);
        assertThat(demandeur.getDossier()).isEqualTo(dossierBack);

        demandeur.dossier(null);
        assertThat(demandeur.getDossier()).isNull();
    }

    @Test
    void eleveTest() {
        Demandeur demandeur = getDemandeurRandomSampleGenerator();
        Eleve eleveBack = getEleveRandomSampleGenerator();

        demandeur.setEleve(eleveBack);
        assertThat(demandeur.getEleve()).isEqualTo(eleveBack);

        demandeur.eleve(null);
        assertThat(demandeur.getEleve()).isNull();
    }

    @Test
    void etudiantTest() {
        Demandeur demandeur = getDemandeurRandomSampleGenerator();
        Etudiant etudiantBack = getEtudiantRandomSampleGenerator();

        demandeur.setEtudiant(etudiantBack);
        assertThat(demandeur.getEtudiant()).isEqualTo(etudiantBack);

        demandeur.etudiant(null);
        assertThat(demandeur.getEtudiant()).isNull();
    }

    @Test
    void professionnelTest() {
        Demandeur demandeur = getDemandeurRandomSampleGenerator();
        Professionnel professionnelBack = getProfessionnelRandomSampleGenerator();

        demandeur.setProfessionnel(professionnelBack);
        assertThat(demandeur.getProfessionnel()).isEqualTo(professionnelBack);

        demandeur.professionnel(null);
        assertThat(demandeur.getProfessionnel()).isNull();
    }

    @Test
    void diplomeTest() {
        Demandeur demandeur = getDemandeurRandomSampleGenerator();
        Diplome diplomeBack = getDiplomeRandomSampleGenerator();

        demandeur.addDiplome(diplomeBack);
        assertThat(demandeur.getDiplomes()).containsOnly(diplomeBack);
        assertThat(diplomeBack.getDemandeur()).isEqualTo(demandeur);

        demandeur.removeDiplome(diplomeBack);
        assertThat(demandeur.getDiplomes()).doesNotContain(diplomeBack);
        assertThat(diplomeBack.getDemandeur()).isNull();

        demandeur.diplomes(new HashSet<>(Set.of(diplomeBack)));
        assertThat(demandeur.getDiplomes()).containsOnly(diplomeBack);
        assertThat(diplomeBack.getDemandeur()).isEqualTo(demandeur);

        demandeur.setDiplomes(new HashSet<>());
        assertThat(demandeur.getDiplomes()).doesNotContain(diplomeBack);
        assertThat(diplomeBack.getDemandeur()).isNull();
    }

    @Test
    void experienceTest() {
        Demandeur demandeur = getDemandeurRandomSampleGenerator();
        Experience experienceBack = getExperienceRandomSampleGenerator();

        demandeur.addExperience(experienceBack);
        assertThat(demandeur.getExperiences()).containsOnly(experienceBack);
        assertThat(experienceBack.getDemandeur()).isEqualTo(demandeur);

        demandeur.removeExperience(experienceBack);
        assertThat(demandeur.getExperiences()).doesNotContain(experienceBack);
        assertThat(experienceBack.getDemandeur()).isNull();

        demandeur.experiences(new HashSet<>(Set.of(experienceBack)));
        assertThat(demandeur.getExperiences()).containsOnly(experienceBack);
        assertThat(experienceBack.getDemandeur()).isEqualTo(demandeur);

        demandeur.setExperiences(new HashSet<>());
        assertThat(demandeur.getExperiences()).doesNotContain(experienceBack);
        assertThat(experienceBack.getDemandeur()).isNull();
    }
}
