package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceMFPAITestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ServiceMFPAI getServiceMFPAISample1() {
        return new ServiceMFPAI().id(1L).nomService("nomService1").chefService("chefService1");
    }

    public static ServiceMFPAI getServiceMFPAISample2() {
        return new ServiceMFPAI().id(2L).nomService("nomService2").chefService("chefService2");
    }

    public static ServiceMFPAI getServiceMFPAIRandomSampleGenerator() {
        return new ServiceMFPAI()
            .id(longCount.incrementAndGet())
            .nomService(UUID.randomUUID().toString())
            .chefService(UUID.randomUUID().toString());
    }
}
