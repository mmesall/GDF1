package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ConcoursTestSamples.*;
import static com.mycompany.myapp.domain.FormationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConcoursTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Concours.class);
        Concours concours1 = getConcoursSample1();
        Concours concours2 = new Concours();
        assertThat(concours1).isNotEqualTo(concours2);

        concours2.setId(concours1.getId());
        assertThat(concours1).isEqualTo(concours2);

        concours2 = getConcoursSample2();
        assertThat(concours1).isNotEqualTo(concours2);
    }

    @Test
    void formationTest() {
        Concours concours = getConcoursRandomSampleGenerator();
        Formation formationBack = getFormationRandomSampleGenerator();

        concours.setFormation(formationBack);
        assertThat(concours.getFormation()).isEqualTo(formationBack);

        concours.formation(null);
        assertThat(concours.getFormation()).isNull();
    }
}
