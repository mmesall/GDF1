package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ExperienceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Experience getExperienceSample1() {
        return new Experience().id(1L).nomEntreprise("nomEntreprise1").posteOccupe("posteOccupe1");
    }

    public static Experience getExperienceSample2() {
        return new Experience().id(2L).nomEntreprise("nomEntreprise2").posteOccupe("posteOccupe2");
    }

    public static Experience getExperienceRandomSampleGenerator() {
        return new Experience()
            .id(longCount.incrementAndGet())
            .nomEntreprise(UUID.randomUUID().toString())
            .posteOccupe(UUID.randomUUID().toString());
    }
}
