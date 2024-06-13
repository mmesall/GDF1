package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PriseEnChargeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PriseEnCharge getPriseEnChargeSample1() {
        return new PriseEnCharge().id(1L).libelle("libelle1");
    }

    public static PriseEnCharge getPriseEnChargeSample2() {
        return new PriseEnCharge().id(2L).libelle("libelle2");
    }

    public static PriseEnCharge getPriseEnChargeRandomSampleGenerator() {
        return new PriseEnCharge().id(longCount.incrementAndGet()).libelle(UUID.randomUUID().toString());
    }
}
