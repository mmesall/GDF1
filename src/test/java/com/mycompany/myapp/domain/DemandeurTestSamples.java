package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DemandeurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Demandeur getDemandeurSample1() {
        return new Demandeur().id(1L).nom("nom1").prenom("prenom1").lieuNaiss("lieuNaiss1").telephone(1L).email("email1");
    }

    public static Demandeur getDemandeurSample2() {
        return new Demandeur().id(2L).nom("nom2").prenom("prenom2").lieuNaiss("lieuNaiss2").telephone(2L).email("email2");
    }

    public static Demandeur getDemandeurRandomSampleGenerator() {
        return new Demandeur()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .prenom(UUID.randomUUID().toString())
            .lieuNaiss(UUID.randomUUID().toString())
            .telephone(longCount.incrementAndGet())
            .email(UUID.randomUUID().toString());
    }
}
