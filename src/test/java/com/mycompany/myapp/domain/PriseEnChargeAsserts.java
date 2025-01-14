package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PriseEnChargeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPriseEnChargeAllPropertiesEquals(PriseEnCharge expected, PriseEnCharge actual) {
        assertPriseEnChargeAutoGeneratedPropertiesEquals(expected, actual);
        assertPriseEnChargeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPriseEnChargeAllUpdatablePropertiesEquals(PriseEnCharge expected, PriseEnCharge actual) {
        assertPriseEnChargeUpdatableFieldsEquals(expected, actual);
        assertPriseEnChargeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPriseEnChargeAutoGeneratedPropertiesEquals(PriseEnCharge expected, PriseEnCharge actual) {
        assertThat(expected)
            .as("Verify PriseEnCharge auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPriseEnChargeUpdatableFieldsEquals(PriseEnCharge expected, PriseEnCharge actual) {
        assertThat(expected)
            .as("Verify PriseEnCharge relevant properties")
            .satisfies(e -> assertThat(e.getLibelle()).as("check libelle").isEqualTo(actual.getLibelle()))
            .satisfies(e -> assertThat(e.getMontantPC()).as("check montantPC").isEqualTo(actual.getMontantPC()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPriseEnChargeUpdatableRelationshipsEquals(PriseEnCharge expected, PriseEnCharge actual) {
        assertThat(expected)
            .as("Verify PriseEnCharge relationships")
            .satisfies(e -> assertThat(e.getFormation()).as("check formation").isEqualTo(actual.getFormation()))
            .satisfies(e -> assertThat(e.getBailleur()).as("check bailleur").isEqualTo(actual.getBailleur()));
    }
}
