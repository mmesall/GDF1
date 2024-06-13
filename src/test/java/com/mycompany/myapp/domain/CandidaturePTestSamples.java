package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CandidaturePTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CandidatureP getCandidaturePSample1() {
        return new CandidatureP().id(1L);
    }

    public static CandidatureP getCandidaturePSample2() {
        return new CandidatureP().id(2L);
    }

    public static CandidatureP getCandidaturePRandomSampleGenerator() {
        return new CandidatureP().id(longCount.incrementAndGet());
    }
}
