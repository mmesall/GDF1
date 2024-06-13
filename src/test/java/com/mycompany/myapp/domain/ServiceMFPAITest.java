package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AgentTestSamples.*;
import static com.mycompany.myapp.domain.ServiceMFPAITestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceMFPAITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceMFPAI.class);
        ServiceMFPAI serviceMFPAI1 = getServiceMFPAISample1();
        ServiceMFPAI serviceMFPAI2 = new ServiceMFPAI();
        assertThat(serviceMFPAI1).isNotEqualTo(serviceMFPAI2);

        serviceMFPAI2.setId(serviceMFPAI1.getId());
        assertThat(serviceMFPAI1).isEqualTo(serviceMFPAI2);

        serviceMFPAI2 = getServiceMFPAISample2();
        assertThat(serviceMFPAI1).isNotEqualTo(serviceMFPAI2);
    }

    @Test
    void agentTest() {
        ServiceMFPAI serviceMFPAI = getServiceMFPAIRandomSampleGenerator();
        Agent agentBack = getAgentRandomSampleGenerator();

        serviceMFPAI.setAgent(agentBack);
        assertThat(serviceMFPAI.getAgent()).isEqualTo(agentBack);
        assertThat(agentBack.getServiceMFPAI()).isEqualTo(serviceMFPAI);

        serviceMFPAI.agent(null);
        assertThat(serviceMFPAI.getAgent()).isNull();
        assertThat(agentBack.getServiceMFPAI()).isNull();
    }
}
