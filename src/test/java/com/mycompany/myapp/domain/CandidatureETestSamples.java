package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CandidatureETestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CandidatureE getCandidatureESample1() {
        return new CandidatureE().id(1L);
    }

    public static CandidatureE getCandidatureESample2() {
        return new CandidatureE().id(2L);
    }

    public static CandidatureE getCandidatureERandomSampleGenerator() {
        return new CandidatureE().id(longCount.incrementAndGet());
    }
}
