package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfessionnelAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProfessionnelAllPropertiesEquals(Professionnel expected, Professionnel actual) {
        assertProfessionnelAutoGeneratedPropertiesEquals(expected, actual);
        assertProfessionnelAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProfessionnelAllUpdatablePropertiesEquals(Professionnel expected, Professionnel actual) {
        assertProfessionnelUpdatableFieldsEquals(expected, actual);
        assertProfessionnelUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProfessionnelAutoGeneratedPropertiesEquals(Professionnel expected, Professionnel actual) {
        assertThat(expected)
            .as("Verify Professionnel auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProfessionnelUpdatableFieldsEquals(Professionnel expected, Professionnel actual) {
        assertThat(expected)
            .as("Verify Professionnel relevant properties")
            .satisfies(e -> assertThat(e.getProfession()).as("check profession").isEqualTo(actual.getProfession()))
            .satisfies(e -> assertThat(e.getNom()).as("check nom").isEqualTo(actual.getNom()))
            .satisfies(e -> assertThat(e.getPrenom()).as("check prenom").isEqualTo(actual.getPrenom()))
            .satisfies(e -> assertThat(e.getDateNaiss()).as("check dateNaiss").isEqualTo(actual.getDateNaiss()))
            .satisfies(e -> assertThat(e.getLieuNaiss()).as("check lieuNaiss").isEqualTo(actual.getLieuNaiss()))
            .satisfies(e -> assertThat(e.getSexe()).as("check sexe").isEqualTo(actual.getSexe()))
            .satisfies(e -> assertThat(e.getTelephone()).as("check telephone").isEqualTo(actual.getTelephone()))
            .satisfies(e -> assertThat(e.getAdressePhysique()).as("check adressePhysique").isEqualTo(actual.getAdressePhysique()))
            .satisfies(e -> assertThat(e.getRegionResidence()).as("check regionResidence").isEqualTo(actual.getRegionResidence()))
            .satisfies(e -> assertThat(e.getDepartResidence()).as("check departResidence").isEqualTo(actual.getDepartResidence()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getCni()).as("check cni").isEqualTo(actual.getCni()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProfessionnelUpdatableRelationshipsEquals(Professionnel expected, Professionnel actual) {}
}