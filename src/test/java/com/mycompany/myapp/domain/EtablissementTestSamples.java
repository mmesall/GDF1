package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EtablissementTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Etablissement getEtablissementSample1() {
        return new Etablissement()
            .id(1L)
            .email("email1")
            .telephone(1L)
            .autreRegion("autreRegion1")
            .autreDepartement("autreDepartement1")
            .autreFiliere("autreFiliere1")
            .autreSerie("autreSerie1")
            .autreNomEtablissement("autreNomEtablissement1");
    }

    public static Etablissement getEtablissementSample2() {
        return new Etablissement()
            .id(2L)
            .email("email2")
            .telephone(2L)
            .autreRegion("autreRegion2")
            .autreDepartement("autreDepartement2")
            .autreFiliere("autreFiliere2")
            .autreSerie("autreSerie2")
            .autreNomEtablissement("autreNomEtablissement2");
    }

    public static Etablissement getEtablissementRandomSampleGenerator() {
        return new Etablissement()
            .id(longCount.incrementAndGet())
            .email(UUID.randomUUID().toString())
            .telephone(longCount.incrementAndGet())
            .autreRegion(UUID.randomUUID().toString())
            .autreDepartement(UUID.randomUUID().toString())
            .autreFiliere(UUID.randomUUID().toString())
            .autreSerie(UUID.randomUUID().toString())
            .autreNomEtablissement(UUID.randomUUID().toString());
    }
}
