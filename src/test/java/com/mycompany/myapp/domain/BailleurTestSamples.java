package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BailleurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bailleur getBailleurSample1() {
        return new Bailleur().id(1L).nomBailleur("nomBailleur1").nbrePC(1L);
    }

    public static Bailleur getBailleurSample2() {
        return new Bailleur().id(2L).nomBailleur("nomBailleur2").nbrePC(2L);
    }

    public static Bailleur getBailleurRandomSampleGenerator() {
        return new Bailleur().id(longCount.incrementAndGet()).nomBailleur(UUID.randomUUID().toString()).nbrePC(longCount.incrementAndGet());
    }
}
