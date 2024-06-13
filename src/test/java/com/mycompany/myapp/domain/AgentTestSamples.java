package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AgentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Agent getAgentSample1() {
        return new Agent()
            .id(1L)
            .matricule("matricule1")
            .nomAgent("nomAgent1")
            .prenom("prenom1")
            .lieuNaiss("lieuNaiss1")
            .telephone(1L)
            .email("email1");
    }

    public static Agent getAgentSample2() {
        return new Agent()
            .id(2L)
            .matricule("matricule2")
            .nomAgent("nomAgent2")
            .prenom("prenom2")
            .lieuNaiss("lieuNaiss2")
            .telephone(2L)
            .email("email2");
    }

    public static Agent getAgentRandomSampleGenerator() {
        return new Agent()
            .id(longCount.incrementAndGet())
            .matricule(UUID.randomUUID().toString())
            .nomAgent(UUID.randomUUID().toString())
            .prenom(UUID.randomUUID().toString())
            .lieuNaiss(UUID.randomUUID().toString())
            .telephone(longCount.incrementAndGet())
            .email(UUID.randomUUID().toString());
    }
}
