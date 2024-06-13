package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FormationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Formation getFormationSample1() {
        return new Formation().id(1L).nomFormation("nomFormation1").duree("duree1");
    }

    public static Formation getFormationSample2() {
        return new Formation().id(2L).nomFormation("nomFormation2").duree("duree2");
    }

    public static Formation getFormationRandomSampleGenerator() {
        return new Formation()
            .id(longCount.incrementAndGet())
            .nomFormation(UUID.randomUUID().toString())
            .duree(UUID.randomUUID().toString());
    }
}
