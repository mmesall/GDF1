package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EtudiantTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Etudiant getEtudiantSample1() {
        return new Etudiant()
            .id(1L)
            .carteEtudiant("carteEtudiant1")
            .nom("nom1")
            .prenom("prenom1")
            .lieuNaiss("lieuNaiss1")
            .telephone(1L)
            .adressePhysique("adressePhysique1")
            .email("email1")
            .cni(1L);
    }

    public static Etudiant getEtudiantSample2() {
        return new Etudiant()
            .id(2L)
            .carteEtudiant("carteEtudiant2")
            .nom("nom2")
            .prenom("prenom2")
            .lieuNaiss("lieuNaiss2")
            .telephone(2L)
            .adressePhysique("adressePhysique2")
            .email("email2")
            .cni(2L);
    }

    public static Etudiant getEtudiantRandomSampleGenerator() {
        return new Etudiant()
            .id(longCount.incrementAndGet())
            .carteEtudiant(UUID.randomUUID().toString())
            .nom(UUID.randomUUID().toString())
            .prenom(UUID.randomUUID().toString())
            .lieuNaiss(UUID.randomUUID().toString())
            .telephone(longCount.incrementAndGet())
            .adressePhysique(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .cni(longCount.incrementAndGet());
    }
}
