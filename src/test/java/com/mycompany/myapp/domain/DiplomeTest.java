package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DemandeurTestSamples.*;
import static com.mycompany.myapp.domain.DiplomeTestSamples.*;
import static com.mycompany.myapp.domain.EleveTestSamples.*;
import static com.mycompany.myapp.domain.EtudiantTestSamples.*;
import static com.mycompany.myapp.domain.ProfessionnelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiplomeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diplome.class);
        Diplome diplome1 = getDiplomeSample1();
        Diplome diplome2 = new Diplome();
        assertThat(diplome1).isNotEqualTo(diplome2);

        diplome2.setId(diplome1.getId());
        assertThat(diplome1).isEqualTo(diplome2);

        diplome2 = getDiplomeSample2();
        assertThat(diplome1).isNotEqualTo(diplome2);
    }

    @Test
    void eleveTest() {
        Diplome diplome = getDiplomeRandomSampleGenerator();
        Eleve eleveBack = getEleveRandomSampleGenerator();

        diplome.setEleve(eleveBack);
        assertThat(diplome.getEleve()).isEqualTo(eleveBack);

        diplome.eleve(null);
        assertThat(diplome.getEleve()).isNull();
    }

    @Test
    void etudiantTest() {
        Diplome diplome = getDiplomeRandomSampleGenerator();
        Etudiant etudiantBack = getEtudiantRandomSampleGenerator();

        diplome.setEtudiant(etudiantBack);
        assertThat(diplome.getEtudiant()).isEqualTo(etudiantBack);

        diplome.etudiant(null);
        assertThat(diplome.getEtudiant()).isNull();
    }

    @Test
    void professionnelTest() {
        Diplome diplome = getDiplomeRandomSampleGenerator();
        Professionnel professionnelBack = getProfessionnelRandomSampleGenerator();

        diplome.setProfessionnel(professionnelBack);
        assertThat(diplome.getProfessionnel()).isEqualTo(professionnelBack);

        diplome.professionnel(null);
        assertThat(diplome.getProfessionnel()).isNull();
    }

    @Test
    void demandeurTest() {
        Diplome diplome = getDiplomeRandomSampleGenerator();
        Demandeur demandeurBack = getDemandeurRandomSampleGenerator();

        diplome.setDemandeur(demandeurBack);
        assertThat(diplome.getDemandeur()).isEqualTo(demandeurBack);

        diplome.demandeur(null);
        assertThat(diplome.getDemandeur()).isNull();
    }
}
