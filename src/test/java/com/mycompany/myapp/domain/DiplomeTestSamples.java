package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DiplomeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Diplome getDiplomeSample1() {
        return new Diplome().id(1L).intitule("intitule1").anneeObtention("anneeObtention1").etablissement("etablissement1");
    }

    public static Diplome getDiplomeSample2() {
        return new Diplome().id(2L).intitule("intitule2").anneeObtention("anneeObtention2").etablissement("etablissement2");
    }

    public static Diplome getDiplomeRandomSampleGenerator() {
        return new Diplome()
            .id(longCount.incrementAndGet())
            .intitule(UUID.randomUUID().toString())
            .anneeObtention(UUID.randomUUID().toString())
            .etablissement(UUID.randomUUID().toString());
    }
}
