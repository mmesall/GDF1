package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AgentAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAgentAllPropertiesEquals(Agent expected, Agent actual) {
        assertAgentAutoGeneratedPropertiesEquals(expected, actual);
        assertAgentAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAgentAllUpdatablePropertiesEquals(Agent expected, Agent actual) {
        assertAgentUpdatableFieldsEquals(expected, actual);
        assertAgentUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAgentAutoGeneratedPropertiesEquals(Agent expected, Agent actual) {
        assertThat(expected)
            .as("Verify Agent auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAgentUpdatableFieldsEquals(Agent expected, Agent actual) {
        assertThat(expected)
            .as("Verify Agent relevant properties")
            .satisfies(e -> assertThat(e.getMatricule()).as("check matricule").isEqualTo(actual.getMatricule()))
            .satisfies(e -> assertThat(e.getNomAgent()).as("check nomAgent").isEqualTo(actual.getNomAgent()))
            .satisfies(e -> assertThat(e.getPrenom()).as("check prenom").isEqualTo(actual.getPrenom()))
            .satisfies(e -> assertThat(e.getDateNaiss()).as("check dateNaiss").isEqualTo(actual.getDateNaiss()))
            .satisfies(e -> assertThat(e.getLieuNaiss()).as("check lieuNaiss").isEqualTo(actual.getLieuNaiss()))
            .satisfies(e -> assertThat(e.getSexe()).as("check sexe").isEqualTo(actual.getSexe()))
            .satisfies(e -> assertThat(e.getTelephone()).as("check telephone").isEqualTo(actual.getTelephone()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAgentUpdatableRelationshipsEquals(Agent expected, Agent actual) {
        assertThat(expected)
            .as("Verify Agent relationships")
            .satisfies(e -> assertThat(e.getServiceMFPAI()).as("check serviceMFPAI").isEqualTo(actual.getServiceMFPAI()));
    }
}