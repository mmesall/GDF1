package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DemandeurAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDemandeurAllPropertiesEquals(Demandeur expected, Demandeur actual) {
        assertDemandeurAutoGeneratedPropertiesEquals(expected, actual);
        assertDemandeurAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDemandeurAllUpdatablePropertiesEquals(Demandeur expected, Demandeur actual) {
        assertDemandeurUpdatableFieldsEquals(expected, actual);
        assertDemandeurUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDemandeurAutoGeneratedPropertiesEquals(Demandeur expected, Demandeur actual) {
        assertThat(expected)
            .as("Verify Demandeur auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDemandeurUpdatableFieldsEquals(Demandeur expected, Demandeur actual) {
        assertThat(expected)
            .as("Verify Demandeur relevant properties")
            .satisfies(e -> assertThat(e.getNom()).as("check nom").isEqualTo(actual.getNom()))
            .satisfies(e -> assertThat(e.getPrenom()).as("check prenom").isEqualTo(actual.getPrenom()))
            .satisfies(e -> assertThat(e.getDateNaiss()).as("check dateNaiss").isEqualTo(actual.getDateNaiss()))
            .satisfies(e -> assertThat(e.getLieuNaiss()).as("check lieuNaiss").isEqualTo(actual.getLieuNaiss()))
            .satisfies(e -> assertThat(e.getSexe()).as("check sexe").isEqualTo(actual.getSexe()))
            .satisfies(e -> assertThat(e.getTelephone()).as("check telephone").isEqualTo(actual.getTelephone()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getProfil()).as("check profil").isEqualTo(actual.getProfil()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDemandeurUpdatableRelationshipsEquals(Demandeur expected, Demandeur actual) {
        assertThat(expected)
            .as("Verify Demandeur relationships")
            .satisfies(e -> assertThat(e.getDossier()).as("check dossier").isEqualTo(actual.getDossier()))
            .satisfies(e -> assertThat(e.getEleve()).as("check eleve").isEqualTo(actual.getEleve()))
            .satisfies(e -> assertThat(e.getEtudiant()).as("check etudiant").isEqualTo(actual.getEtudiant()))
            .satisfies(e -> assertThat(e.getProfessionnel()).as("check professionnel").isEqualTo(actual.getProfessionnel()));
    }
}