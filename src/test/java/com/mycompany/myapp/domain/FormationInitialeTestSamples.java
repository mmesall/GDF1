package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FormationInitialeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FormationInitiale getFormationInitialeSample1() {
        return new FormationInitiale()
            .id(1L)
            .nomFormationI("nomFormationI1")
            .duree("duree1")
            .nomConcours("nomConcours1")
            .nomDebouche("nomDebouche1");
    }

    public static FormationInitiale getFormationInitialeSample2() {
        return new FormationInitiale()
            .id(2L)
            .nomFormationI("nomFormationI2")
            .duree("duree2")
            .nomConcours("nomConcours2")
            .nomDebouche("nomDebouche2");
    }

    public static FormationInitiale getFormationInitialeRandomSampleGenerator() {
        return new FormationInitiale()
            .id(longCount.incrementAndGet())
            .nomFormationI(UUID.randomUUID().toString())
            .duree(UUID.randomUUID().toString())
            .nomConcours(UUID.randomUUID().toString())
            .nomDebouche(UUID.randomUUID().toString());
    }
}
