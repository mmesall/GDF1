package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DemandeurTestSamples.*;
import static com.mycompany.myapp.domain.DossierTestSamples.*;
import static com.mycompany.myapp.domain.EleveTestSamples.*;
import static com.mycompany.myapp.domain.EtudiantTestSamples.*;
import static com.mycompany.myapp.domain.ProfessionnelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DossierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dossier.class);
        Dossier dossier1 = getDossierSample1();
        Dossier dossier2 = new Dossier();
        assertThat(dossier1).isNotEqualTo(dossier2);

        dossier2.setId(dossier1.getId());
        assertThat(dossier1).isEqualTo(dossier2);

        dossier2 = getDossierSample2();
        assertThat(dossier1).isNotEqualTo(dossier2);
    }

    @Test
    void eleveTest() {
        Dossier dossier = getDossierRandomSampleGenerator();
        Eleve eleveBack = getEleveRandomSampleGenerator();

        dossier.setEleve(eleveBack);
        assertThat(dossier.getEleve()).isEqualTo(eleveBack);

        dossier.eleve(null);
        assertThat(dossier.getEleve()).isNull();
    }

    @Test
    void etudiantTest() {
        Dossier dossier = getDossierRandomSampleGenerator();
        Etudiant etudiantBack = getEtudiantRandomSampleGenerator();

        dossier.setEtudiant(etudiantBack);
        assertThat(dossier.getEtudiant()).isEqualTo(etudiantBack);

        dossier.etudiant(null);
        assertThat(dossier.getEtudiant()).isNull();
    }

    @Test
    void professionnelTest() {
        Dossier dossier = getDossierRandomSampleGenerator();
        Professionnel professionnelBack = getProfessionnelRandomSampleGenerator();

        dossier.setProfessionnel(professionnelBack);
        assertThat(dossier.getProfessionnel()).isEqualTo(professionnelBack);

        dossier.professionnel(null);
        assertThat(dossier.getProfessionnel()).isNull();
    }

    @Test
    void demandeurTest() {
        Dossier dossier = getDossierRandomSampleGenerator();
        Demandeur demandeurBack = getDemandeurRandomSampleGenerator();

        dossier.setDemandeur(demandeurBack);
        assertThat(dossier.getDemandeur()).isEqualTo(demandeurBack);
        assertThat(demandeurBack.getDossier()).isEqualTo(dossier);

        dossier.demandeur(null);
        assertThat(dossier.getDemandeur()).isNull();
        assertThat(demandeurBack.getDossier()).isNull();
    }
}
