package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ConcoursTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Concours getConcoursSample1() {
        return new Concours().id(1L).nomConcours("nomConcours1");
    }

    public static Concours getConcoursSample2() {
        return new Concours().id(2L).nomConcours("nomConcours2");
    }

    public static Concours getConcoursRandomSampleGenerator() {
        return new Concours().id(longCount.incrementAndGet()).nomConcours(UUID.randomUUID().toString());
    }
}
