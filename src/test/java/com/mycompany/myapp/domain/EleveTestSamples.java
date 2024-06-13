package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EleveTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Eleve getEleveSample1() {
        return new Eleve()
            .id(1L)
            .nom("nom1")
            .prenom("prenom1")
            .lieuNaiss("lieuNaiss1")
            .telephone(1L)
            .adressePhysique("adressePhysique1")
            .cni(1L);
    }

    public static Eleve getEleveSample2() {
        return new Eleve()
            .id(2L)
            .nom("nom2")
            .prenom("prenom2")
            .lieuNaiss("lieuNaiss2")
            .telephone(2L)
            .adressePhysique("adressePhysique2")
            .cni(2L);
    }

    public static Eleve getEleveRandomSampleGenerator() {
        return new Eleve()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .prenom(UUID.randomUUID().toString())
            .lieuNaiss(UUID.randomUUID().toString())
            .telephone(longCount.incrementAndGet())
            .adressePhysique(UUID.randomUUID().toString())
            .cni(longCount.incrementAndGet());
    }
}
