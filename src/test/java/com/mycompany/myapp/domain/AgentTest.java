package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AgentTestSamples.*;
import static com.mycompany.myapp.domain.ServiceMFPAITestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agent.class);
        Agent agent1 = getAgentSample1();
        Agent agent2 = new Agent();
        assertThat(agent1).isNotEqualTo(agent2);

        agent2.setId(agent1.getId());
        assertThat(agent1).isEqualTo(agent2);

        agent2 = getAgentSample2();
        assertThat(agent1).isNotEqualTo(agent2);
    }

    @Test
    void serviceMFPAITest() {
        Agent agent = getAgentRandomSampleGenerator();
        ServiceMFPAI serviceMFPAIBack = getServiceMFPAIRandomSampleGenerator();

        agent.setServiceMFPAI(serviceMFPAIBack);
        assertThat(agent.getServiceMFPAI()).isEqualTo(serviceMFPAIBack);

        agent.serviceMFPAI(null);
        assertThat(agent.getServiceMFPAI()).isNull();
    }
}
