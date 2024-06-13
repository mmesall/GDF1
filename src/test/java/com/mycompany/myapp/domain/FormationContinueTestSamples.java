package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FormationContinueTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FormationContinue getFormationContinueSample1() {
        return new FormationContinue()
            .id(1L)
            .nomFormationC("nomFormationC1")
            .duree("duree1")
            .libellePC("libellePC1")
            .autreDiplome("autreDiplome1")
            .nomDebouche("nomDebouche1");
    }

    public static FormationContinue getFormationContinueSample2() {
        return new FormationContinue()
            .id(2L)
            .nomFormationC("nomFormationC2")
            .duree("duree2")
            .libellePC("libellePC2")
            .autreDiplome("autreDiplome2")
            .nomDebouche("nomDebouche2");
    }

    public static FormationContinue getFormationContinueRandomSampleGenerator() {
        return new FormationContinue()
            .id(longCount.incrementAndGet())
            .nomFormationC(UUID.randomUUID().toString())
            .duree(UUID.randomUUID().toString())
            .libellePC(UUID.randomUUID().toString())
            .autreDiplome(UUID.randomUUID().toString())
            .nomDebouche(UUID.randomUUID().toString());
    }
}
