package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceMFPAIAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiceMFPAIAllPropertiesEquals(ServiceMFPAI expected, ServiceMFPAI actual) {
        assertServiceMFPAIAutoGeneratedPropertiesEquals(expected, actual);
        assertServiceMFPAIAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiceMFPAIAllUpdatablePropertiesEquals(ServiceMFPAI expected, ServiceMFPAI actual) {
        assertServiceMFPAIUpdatableFieldsEquals(expected, actual);
        assertServiceMFPAIUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiceMFPAIAutoGeneratedPropertiesEquals(ServiceMFPAI expected, ServiceMFPAI actual) {
        assertThat(expected)
            .as("Verify ServiceMFPAI auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiceMFPAIUpdatableFieldsEquals(ServiceMFPAI expected, ServiceMFPAI actual) {
        assertThat(expected)
            .as("Verify ServiceMFPAI relevant properties")
            .satisfies(e -> assertThat(e.getImageService()).as("check imageService").isEqualTo(actual.getImageService()))
            .satisfies(
                e ->
                    assertThat(e.getImageServiceContentType())
                        .as("check imageService contenty type")
                        .isEqualTo(actual.getImageServiceContentType())
            )
            .satisfies(e -> assertThat(e.getNomService()).as("check nomService").isEqualTo(actual.getNomService()))
            .satisfies(e -> assertThat(e.getChefService()).as("check chefService").isEqualTo(actual.getChefService()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiceMFPAIUpdatableRelationshipsEquals(ServiceMFPAI expected, ServiceMFPAI actual) {}
}
